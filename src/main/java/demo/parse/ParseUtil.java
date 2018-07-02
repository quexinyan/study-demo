package demo.parse;/*
 * Time : 2018/7/2 21:06
 * Author : gaox
 * Description :
 */

/**
 * @Time           2018/7/2 21:06
 * @Author          gaox
 * @Description     占位符替换工具类
*/
public class ParseUtil {

    /**
    * 将字符串text中由openToken和closeToken组成的占位符依次替换为args数组中的值
    * @author      gaox
    * @date        2018/7/2 21:07
    */
    public static String parse(String openToken, String closeToken, String text, Object... args) {
        if (args == null || args.length <= 0) {
            return text;
        }
        int argsIndex = 0;

        if (text == null || text.isEmpty()) {
            return "";
        }
        char[] src = text.toCharArray();
        int offset = 0;
        // search open token
        int start = text.indexOf(openToken, offset);
        if (start == -1) {
            return text;
        }
        final StringBuilder builder = new StringBuilder();
        StringBuilder expression = null;
        while (start > -1) {
            if (start > 0 && src[start - 1] == '\\') {
                // this open token is escaped. remove the backslash and continue.
                builder.append(src, offset, start - offset - 1).append(openToken);
                offset = start + openToken.length();
            } else {
                // found open token. let's search close token.
                if (expression == null) {
                    expression = new StringBuilder();
                } else {
                    expression.setLength(0);
                }
                builder.append(src, offset, start - offset);
                offset = start + openToken.length();
                int end = text.indexOf(closeToken, offset);
                while (end > -1) {
                    if (end > offset && src[end - 1] == '\\') {
                        // this close token is escaped. remove the backslash and continue.
                        expression.append(src, offset, end - offset - 1).append(closeToken);
                        offset = end + closeToken.length();
                        end = text.indexOf(closeToken, offset);
                    } else {
                        expression.append(src, offset, end - offset);
                        offset = end + closeToken.length();
                        break;
                    }
                }
                if (end == -1) {
                    // close token was not found.
                    builder.append(src, start, src.length - start);
                    offset = src.length;
                } else {
                    String value = (argsIndex <= args.length - 1) ?
                            (args[argsIndex] == null ? "" : args[argsIndex].toString()) : expression.toString();
                    builder.append(value);
                    offset = end + closeToken.length();
                    argsIndex++;
                }
            }
            start = text.indexOf(openToken, offset);
        }
        if (offset < src.length) {
            builder.append(src, offset, src.length - offset);
        }
        return builder.toString();
    }

    public static String parse0(String text, Object... args) {
        return ParseUtil.parse("${", "}", text, args);
    }


    public static String parse1(String text, Object... args) {
        return ParseUtil.parse("{", "}", text, args);
    }


    public static void main(String[] args) {
        System.out.println(parse1("【58车贷】恭喜您成功注册58车贷，您的账号：{1}，初始密码：{2}。为保障你的账户安全，请尽快到58车贷会员中心修改密码！",
                "13023277376,","888888"));
    }
}
