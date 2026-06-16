package com.thealgorithms.tree;

import java.util.ArrayList;
import java.util.List;

// 基础节点,基础节点中有一个判断该当前节点是否为叶子节点
// B+树的结构稍微明确一下,中间节点只有key,帮助导航到叶子节点,数据只存储在叶子节点上
// 每个子节点上有多个数据,所能容纳的数据的上限称为阶数.相邻的叶子节点中还有一个单向的指针
// 我们在向其中添加数据后要判断是否超过阶数,超过需要自底向上进行分裂
// 所以在进行B+树的排序时,首先从根节点开始根据key找到最底部的叶子节点,
// 然后在叶子节点内部遍历找到合适的插入位置,插入后如果超过阶数就向上分裂
abstract class Node {
    boolean isLeaf;
    // 这个存储的key就是作为主键用来指向不同区间的起始值
    List<Integer> keys = new ArrayList<>(); // 存储 Key
    // 这个节点用于指向父节点
    Node parent; // 指向父亲，用于向上分裂
}

// 叶子节点：只存数据
class LeafNode extends Node {
    // 对于子节点,新增一个values的List专门用来存储具体的值
    List<String> values = new ArrayList<>(); // 存储真正的业务数据 Value
    // 这个next是指水平方向上各个叶子节点向后指向,这个可以优化范围查询时的索引
    LeafNode next; // 双向链表指针

    LeafNode() { this.isLeaf = true; }
}

// 内节点：只存索引指路，不存 Value
class InternalNode extends Node {
    // 要明白这个和keys(挡板)的区别
    // 前者是用来做判断的依据,这个才是我们真正指向子节点的值
    // 也就是重点要明白key和nodes的区别
    List<Node> children = new ArrayList<>(); // 存储子节点指针

    InternalNode() { this.isLeaf = false; }
}
public class BPlusTree {
    private static final int M = 4; // 4阶B+树，最多3个Key，4个Key时分裂
    private Node root;
    // 我们的B+树的初始化首先只有一个叶子节点,因为为了保证所有子节点都在同一层
    // 实际上写入时实现向上分裂
    public BPlusTree() {
        this.root = new LeafNode(); // 刚开始只有一层叶子节点
    }

    /**
     * 核心插入方法
     */
    public void insert(int key, String value) {
        LeafNode leaf = findLeafNode(root, key);
        // 1. 无脑插入到叶子节点
        insertIntoLeaf(leaf, key, value);
        // 这里是核心的升级策略,根据之前的步骤我们找到的最可能的叶子节点并且插入
        // 2. 如果叶子节点满了（达到 M 个 Key），触发向上分裂
        if (leaf.keys.size() >= M) {
            split(leaf);
        }
    }

    /**
     * 核心分裂方法：支持叶子节点和内节点的分裂
     */
    private void split(Node node) {
        // 我们的叶子节点从中间开始分裂
        int midIdx = node.keys.size() / 2; // 找到中位数索引
        int upKey = node.keys.get(midIdx); // 准备往上提拔的 Key

        Node newNode;
        // 我们入口正常来说是叶子节点,但是拆分的过程中会向上添加节点
        // 所以我们可能会递归调用该方法,因此需要判断当前节点的类型
        if (node.isLeaf) {
            // --- 情况 A：叶子节点分裂 ---
            LeafNode leaf = (LeafNode) node;
            LeafNode newLeaf = new LeafNode();

            // 将右半部分的数据剪切给新叶子
            newLeaf.keys.addAll(leaf.keys.subList(midIdx, leaf.keys.size()));
            newLeaf.values.addAll(leaf.values.subList(midIdx, leaf.values.size()));
            leaf.keys.subList(midIdx, leaf.keys.size()).clear();
            leaf.values.subList(midIdx, leaf.values.size()).clear();

            // 维护底层叶子节点的双向链表指针
            newLeaf.next = leaf.next;
            leaf.next = newLeaf;
            // 将我们if{}里面内部的newLeaf赋值给newNode
            newNode = newLeaf;
        } else {
            // --- 情况 B：内节点（目录层）分裂 ---
            InternalNode internal = (InternalNode) node;
            InternalNode newInternal = new InternalNode();

            // 将右半部分的索引和子节点剪切给新内节点
            // 注意：提拔上去了 upKey，所以右边的 keys 从 midIdx + 1 开始
            newInternal.keys.addAll(internal.keys.subList(midIdx + 1, internal.keys.size()));
            newInternal.children.addAll(internal.children.subList(midIdx + 1, internal.children.size()));

            internal.keys.subList(midIdx, internal.keys.size()).clear(); // 包含中间那个丢掉的
            internal.children.subList(midIdx + 1, internal.children.size()).clear();

            // 修正新子节点的父亲指向
            for (Node child : newInternal.children) {
                child.parent = newInternal;
            }

            newNode = newInternal;
        }
        // 这里让新创建newnode指向共有的父节点
        newNode.parent = node.parent;

        // --- 核心：向上提拔（向父亲塞入 upKey） ---
        if (node.parent == null) {
            // 如果当前分裂的是根节点（没父亲），说明树要长高一层了！
            InternalNode newRoot = new InternalNode();
            newRoot.keys.add(upKey);
            newRoot.children.add(node);
            newRoot.children.add(newNode);

            node.parent = newRoot;
            newNode.parent = newRoot;
            this.root = newRoot; // 树根易主，全员整齐划一降一层
        } else {
            // 如果有父亲，把 upKey 和新生成的节点指针，塞进现有的父亲节点里
            InternalNode parent = (InternalNode) node.parent;
            int idx = insertIntoInternal(parent, upKey, newNode);

            // 【递归的精髓】：如果父亲塞入新 Key 后也满了，继续向上分裂父亲！
            // 这里是重点,如果塞入后满了就递归调用上述逻辑
            if (parent.keys.size() >= M) {
                split(parent);
            }
        }
    }

    // 辅助方法：寻找对应的叶子节点
    // 这里注意实际上并不是直接匹配,而是找到key多应该落在的叶子节点上
    // 而叶子节点上实际上存放的也是一个values的数组,我们只是找到key最有可能落到的叶子节点
    private LeafNode findLeafNode(Node current, int key) {
        // 当我们进行插入时,需要从根节点开始向下查询,找到对应的叶子节点应该所在的位置
        // 如果当前节点已经是叶子节点,直接返回,直接返回即可
        // 这里也是递归函数的结束的地方
        if (current.isLeaf) return (LeafNode) current;
        // 否则说明是internalNode(root如果有子节点则也属于InternalNode)
        InternalNode internal = (InternalNode) current;
        for (int i = 0; i < internal.keys.size(); i++) {
            // 还有一个简单的对应关系,挡板0对应node0,挡板1对应node1,想想对应的图

            //这里就是递归查询,入参key首先要和挡板(keys)进行比较,如果小于当前挡板就流向当前挡板对应的node
            // 之后重复该方法知道找到子节点
            if (key < internal.keys.get(i)) return findLeafNode(internal.children.get(i), key);
        }
        // 能够走到这一步说明入参key要大于目前所有的挡板,此时我们只需要将其导向最右边的节点就行
        return findLeafNode(internal.children.get(internal.children.size() - 1), key);
    }

    // 辅助方法：有序插入叶子
    private void insertIntoLeaf(LeafNode leaf, int key, String value) {
        int i = 0;
        // 我们找到了目标值对应的key最可能所在的叶子节点,然后开始遍历直到找到合适的key的插入位置
        while (i < leaf.keys.size() && leaf.keys.get(i) < key) i++;
        // 在每个叶子节点内部,key是严格递增的
        leaf.keys.add(i, key);
        leaf.values.add(i, value);
    }

    // 辅助方法：有序插入内节点，并返回插入的位置
    private int insertIntoInternal(InternalNode parent, int key, Node child) {
        int i = 0;
        while (i < parent.keys.size() && parent.keys.get(i) < key) i++;
        parent.keys.add(i, key);
        parent.children.add(i + 1, child); // 子节点指针插在 key 的后面
        return i;
    }
    /**
     * 核心删除方法入口
     */
    public void delete(int key) {
        // 1. 利用你已有的 findLeafNode 找到对应的叶子节点
        LeafNode leaf = findLeafNode(root, key);

        // 2. 尝试在叶子节点中找到该 Key
        int idx = leaf.keys.indexOf(key);
        if (idx == -1) {
            return; // 树中没有这个 key，直接返回
        }

        // 3. 直接从叶子节点中移除 Key 和 Value
        leaf.keys.remove(idx);
        leaf.values.remove(idx);

        // 4. 如果删除的是叶子节点的第一个元素，且它有父亲，需要沿着父亲向上更新“挡板”索引
        if (idx == 0 && leaf.parent != null && !leaf.keys.isEmpty()) {
            updateIndex(leaf.parent, key, leaf.keys.get(0));
        }

        // 5. 检查删除后是否触发下限（4阶树下限为1：即至少需要 1 个 Key）
        // 如果当前节点已经是根节点，则允许清空（即树被删光了）
        if (leaf != root && leaf.keys.size() < (M / 2 - 1)) {
            handleUnderflow(leaf);
        }
    }

    /**
     * 核心借调与合并调整方法：支持叶子和内节点的下限恢复（自底向上递归）
     */
    private void handleUnderflow(Node node) {
        // 如果已经递归调整到了根节点
        if (node == root) {
            // 如果根节点是内节点，且被删得没有 key 了，说明树要矮一层
            if (!node.isLeaf && node.keys.isEmpty()) {
                InternalNode internalRoot = (InternalNode) root;
                this.root = internalRoot.children.get(0); // 树根易主，全员整齐划一升一层
                this.root.parent = null;
            }
            return;
        }

        InternalNode parent = (InternalNode) node.parent;
        int myIdx = parent.children.indexOf(node); // 找到自己在父亲中的指针索引

        // 寻找它的亲左兄弟和亲右兄弟
        Node leftBrother = (myIdx > 0) ? parent.children.get(myIdx - 1) : null;
        Node rightBrother = (myIdx < parent.children.size() - 1) ? parent.children.get(myIdx + 1) : null;

        int minKeys = M / 2 - 1; // 下限：1 个 Key

        if (node.isLeaf) {
            // ==========================================
            // 情况 A：叶子节点的下限调整
            // ==========================================
            LeafNode currentLeaf = (LeafNode) node;

            // 1. 尝试向左兄弟借
            if (leftBrother != null && leftBrother.keys.size() > minKeys) {
                LeafNode left = (LeafNode) leftBrother;
                // 挪动左边最右侧的数据到自己最左侧
                currentLeaf.keys.add(0, left.keys.remove(left.keys.size() - 1));
                currentLeaf.values.add(0, left.values.remove(left.values.size() - 1));
                // 更新父亲对应的挡板值
                parent.keys.set(myIdx - 1, currentLeaf.keys.get(0));
                return;
            }

            // 2. 尝试向右兄弟借
            if (rightBrother != null && rightBrother.keys.size() > minKeys) {
                LeafNode right = (LeafNode) rightBrother;
                // 挪动右边最左侧的数据到自己最右侧
                currentLeaf.keys.add(right.keys.remove(0));
                currentLeaf.values.add(right.values.remove(0));
                // 更新父亲对应的挡板值
                parent.keys.set(myIdx, right.keys.get(0));
                // 如果右兄弟借走的是第一个，右兄弟的父亲挡板可能也需要同步更新
                if (!right.keys.isEmpty()) {
                    updateIndex(parent, parent.keys.get(myIdx), right.keys.get(0));
                }
                return;
            }

            // 3. 借不到，被迫破产合并（优先和左兄弟合并）
            if (leftBrother != null) {
                LeafNode left = (LeafNode) leftBrother;
                // 将自己所有的内容追加到左兄弟
                left.keys.addAll(currentLeaf.keys);
                left.values.addAll(currentLeaf.values);
                // 维护底层叶子节点的单向链表
                left.next = currentLeaf.next;

                // 从父亲中抽走用于分隔这两者的“挡板”Key 和当前节点的 child 指针
                parent.keys.remove(myIdx - 1);
                parent.children.remove(myIdx);

                // 【递归精髓】：如果父亲被抽走 Key 后也低于下限了，继续向上递归调整父亲！
                if (parent.keys.size() < minKeys) {
                    handleUnderflow(parent);
                }
            } else if (rightBrother != null) {
                // 和右兄弟合并（把自己融进右兄弟）
                LeafNode right = (LeafNode) rightBrother;
                currentLeaf.keys.addAll(right.keys);
                currentLeaf.values.addAll(right.values);
                currentLeaf.next = right.next;

                parent.keys.remove(myIdx);
                parent.children.remove(myIdx + 1);

                if (parent.keys.size() < minKeys) {
                    handleUnderflow(parent);
                }
            }

        } else {
            // ==========================================
            // 情况 B：内节点（目录层）的下限调整
            // ==========================================
            InternalNode currentInternal = (InternalNode) node;

            // 1. 尝试向左内节点兄弟借
            if (leftBrother != null && leftBrother.keys.size() > minKeys) {
                InternalNode left = (InternalNode) leftBrother;
                // 注意：内节点借调时，需要借助父亲的“挡板Key”降级下来顶替
                int parentKeyIdx = myIdx - 1;
                currentInternal.keys.add(0, parent.keys.get(parentKeyIdx));
                // 父亲的挡板换成左兄弟的最右侧 Key
                parent.keys.set(parentKeyIdx, left.keys.remove(left.keys.size() - 1));

                // 转移对应的子节点指针，并修正父亲指向
                Node movedChild = left.children.remove(left.children.size() - 1);
                currentInternal.children.add(0, movedChild);
                movedChild.parent = currentInternal;
                return;
            }

            // 2. 尝试向右内节点兄弟借
            if (rightBrother != null && rightBrother.keys.size() > minKeys) {
                InternalNode right = (InternalNode) rightBrother;
                int parentKeyIdx = myIdx;
                currentInternal.keys.add(parent.keys.get(parentKeyIdx));
                parent.keys.set(parentKeyIdx, right.keys.remove(0));

                Node movedChild = right.children.remove(0);
                currentInternal.children.add(movedChild);
                movedChild.parent = currentInternal;
                return;
            }

            // 3. 借不到，内节点被迫破产合并
            if (leftBrother != null) {
                InternalNode left = (InternalNode) leftBrother;
                // 把父亲层夹在中间的“挡板Key”拉下来，作为合并后的连接 Key
                left.keys.add(parent.keys.remove(myIdx - 1));
                // 把自己所有的 keys 和 children 拼接到左兄弟后面
                left.keys.addAll(currentInternal.keys);
                left.children.addAll(currentInternal.children);

                // 修正挪过去的所有子节点的父亲指向
                for (Node child : currentInternal.children) {
                    child.parent = left;
                }

                parent.children.remove(myIdx);

                // 检查父亲是否受连带责任塌陷
                if (parent.keys.size() < minKeys) {
                    handleUnderflow(parent);
                }
            } else if (rightBrother != null) {
                InternalNode right = (InternalNode) rightBrother;
                currentInternal.keys.add(parent.keys.remove(myIdx));
                currentInternal.keys.addAll(right.keys);
                currentInternal.children.addAll(right.children);

                for (Node child : right.children) {
                    child.parent = currentInternal;
                }

                parent.children.remove(myIdx + 1);

                if (parent.keys.size() < minKeys) {
                    handleUnderflow(parent);
                }
            }
        }
    }

    /**
     * 辅助方法：当叶子节点的起始 Key 发生改变时，向上层目录中修正老旧的“挡板”索引
     */
    private void updateIndex(Node current, int oldKey, int newKey) {
        if (current == null) return;
        InternalNode internal = (InternalNode) current;
        int idx = internal.keys.indexOf(oldKey);
        if (idx != -1) {
            internal.keys.set(idx, newKey); // 抓到现形，直接替换成新挡板
        } else {
            // 没在当前层找到，继续递归往它的父层去找
            updateIndex(internal.parent, oldKey, newKey);
        }
    }

}
