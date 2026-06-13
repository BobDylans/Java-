package com.thealgorithms.greedyalgorithms;

/* Gale-Shapley稳定婚姻匹配 */


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Implementation of the Gale-Shapley Algorithm for Stable Matching.
 * Problem link: https://en.wikipedia.org/wiki/Stable_marriage_problem
 */
public final class GaleShapley {

    private GaleShapley() {
    }

    /**
     * Function to find stable matches between men and women.
     *
     * @param womenPrefs A map containing women's preferences where each key is a woman and the value is an array of men in order of preference.
     * @param menPrefs   A map containing men's preferences where each key is a man and the value is an array of women in order of preference.
     * @return A map containing stable matches where the key is a woman and the value is her matched man.
     */
    public static Map<String, String> stableMatch(Map<String, LinkedList<String>> womenPrefs, Map<String, LinkedList<String>> menPrefs) {
        // Initialize all men as free
        // 首先你要理解一下两个入参的含义,分别是两个map,其中key就是男(女)人的代号,后面的list就是他们心仪的排行表
        // 排行表上越靠前的说明越心仪 
        Map<String, String> engagements = new HashMap<>();
        // 我们优先取出单身汉列表
        // 对于单身汉来说,每次求婚,
        // 成功的情况是 1.女人没有订婚,直接成功
        //              2.女人订婚了,但是自己在女方的心仪列表上更靠前,所以订婚 
        // 失败的情况是 女人订婚且自己比不过未婚夫(即上述两个情况都不满足),回到单身汉序列重新等待
        //  有意思的是,因为女方是优先答应,之后根据男方的求婚来决定的,因此女方在博弈学中总是选择稳定系统中最差的男人
    //      而男人因为总是选最好的,所以最后会选择稳定系统中最好的女人(当然如果女追男其实是一样的,只是简单的逻辑问题)
        LinkedList<String> freeMen = new LinkedList<>(menPrefs.keySet());

        // While there are free men
        // 当单身好还存在时就一直尝试求婚 
        while (!freeMen.isEmpty()) {
            String man = freeMen.poll(); // Get the first free man
            // 我们从单身汉队列中取出一个,然后获取他的心仪列表 
            LinkedList<String> manPref = menPrefs.get(man); // Get the preferences of the man

            // Check if manPref is null or empty
            if (manPref == null || manPref.isEmpty()) {
                continue; // Skip if no preferences
            }

            // Propose to the first woman in the man's preference list
            // 取出心仪列表中第一个女人,并且尝试看看该女人是不是已经有未婚夫
            String woman = manPref.poll();
            String fiance = engagements.get(woman);

            // If the woman is not engaged, engage her with the current man
            // 如果尚未订婚,就和当前的单身汉配对,二者订婚  
            if (fiance == null) {
                engagements.put(woman, man);
            } else {
                // If the woman prefers the current man over her current fiance
                // 否则这个订婚的女人就去看自己的心仪列表,如果当前这个男人的排名比自己的未婚夫靠前
                // 就和这个新的man订婚 
                LinkedList<String> womanPrefList = womenPrefs.get(woman);

                // Check if womanPrefList is null
                if (womanPrefList == null) {
                    continue; // Skip if no preferences for the woman
                }

                if (womanPrefList.indexOf(man) < womanPrefList.indexOf(fiance)) {
                    engagements.put(woman, man);
                    freeMen.add(fiance); // Previous fiance becomes free
                } else {
                    // Woman rejects the new proposal, the man remains free
                    // 否则这个男人就回到单身汉序列,继续排队 
                    freeMen.add(man);
                }
            }
        }
        return engagements; // Return the stable matches
    }
}
