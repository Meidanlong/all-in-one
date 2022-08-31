package com.meidl.leetcode.editor.cn;

import java.util.HashMap;

// [1] 两数之和
public class TwoSum{
  public static void main(String[] args) {
       Solution solution = new TwoSum().new Solution();
  }
    
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] twoSum(int[] nums, int target) {
        // 维护 val -> index 的映射
        HashMap<Integer, Integer> valToIndex = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            // 查表，看看是否有能和 nums[i] 凑出 target 的元素
            int need = target - nums[i];
            if (valToIndex.containsKey(need)) {
                return new int[]{valToIndex.get(need), i};
            }
            // 存入 val -> index 的映射
            valToIndex.put(nums[i], i);
        }
        return null;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}