package util;


import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author adorabled4
 * @className TestUtil
 * @date : 2023/05/23/ 15:56
 **/
public class TestUtil {

    /**
     * 生成随机序列
     *
     * @param size
     * @param min
     * @param max
     * @return
     */
    public static int[] generateRequestArray(int size, int min, int max) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = (Math.abs(random.nextInt()) + min) % max;
        }
        return array;
    }

    /**
     * 打印数组
     *
     * @param nums
     */
    public static void printArray(int[] nums) {
        StringBuffer sb = new StringBuffer("");
        sb.append("[");
        for (int i = 0; i < nums.length; i++) {
            sb.append(nums[i]).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        System.out.println(sb);
    }

    /**
     * 一般测试 , 通过参数自动生成数据进行测试
     *
     * @param algorithm
     * @param size
     * @param min
     * @param max
     * @param head
     */
    public static void algorithmTest(Class<?> algorithm, int size, int min, int max, int head) {
        try {
            // 获取 构造器
            Constructor<?> constructor = algorithm.getConstructor(int.class, int[].class, int.class);
            int[] request = TestUtil.generateRequestArray(size, min, max); // 磁盘请求序列
            System.out.println("测试开始" +
                    "\t本次测试算法为: " + algorithm.getSimpleName() +
                    "\t本次调度数组大小: " + size +
                    "\t磁头初始位置: " + head +
                    "\t磁盘请求序列为: ");
            printArray(request);
            // 获取 对象实例
            Object instance = constructor.newInstance(size, request, head);
            Method schedule = algorithm.getMethod("schedule");
            // 执行调度
            schedule.invoke(instance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 用于对比测试
     * @param classes
     * @param size
     * @param min
     * @param max
     * @param head
     */
    public static void algorithmTest(Class[] classes, int size, int min, int max, int head) {
        Map<String, String> map = new HashMap<>(); // name : testResult
        int[] request = TestUtil.generateRequestArray(size, min, max); // 磁盘请求序列
        for (Class algorithm : classes) {
            try {
                // 获取 构造器
                Constructor<?> constructor = algorithm.getConstructor(int.class, int[].class, int.class);
                System.out.println("\n测试开始" +
                        "\t本次测试算法为: \033[1m" + algorithm.getSimpleName() +
                        "\033[0m\t本次调度数组大小: " + size +
                        "\t磁头初始位置: " + head +
                        "\t磁盘请求序列为: ");
                // 获取 对象实例
                Object instance = constructor.newInstance(size, request, head);
                Method schedule = algorithm.getMethod("schedule");
                // 执行调度
                Object value = schedule.invoke(instance);
                Integer seekTime = (Integer) value;
                StringBuilder sb = new StringBuilder("");
                sb.append("总移动距离为：").append(seekTime).append("，平均寻道长度为：").append((double) seekTime / size);
                // 存储数据
                map.put(algorithm.getSimpleName(), sb.toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        // 打印结果
        System.out.println("\u001B[34m\u001B[1m[对比测试完毕]\u001B[0m");
        System.out.printf("%-20s%-25s\n", "\033[1m算法名称\033[0m", "\t\t\033[1m平均寻道长度\033[0m");
        map.forEach((key, value) -> System.out.printf("%-20s%-25s\n", key, value));
    }
}
