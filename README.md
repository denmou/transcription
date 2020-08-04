# transcription
模版转录

## 功能说明
将模版转录为实际字符串信息

## 示例
```
    /**
     * 酶实例
     * 提供数据处理方法
     * @Polymerase 未指定实例名称时，使用类名称作为实例名称
     */
    @Polymerase(name = "code")
    class Code {
        public String join(String n, String m, String l) {
            return n + m + l;
        }

        public String getString(Double n) {
            return String.valueOf(n);
        }

        public Long count(Integer... array) {
            Long result = 0L;
            for (Integer value : array) {
                result += value;
            }
            return result;
        }
    }

    public static void main(String[] args) throws ExpressException {
        Code code = new Code();
        String template = "result:\n \tjoin: <code@join(string#It's \\[,code@getString(double#3.14),string#\\])>\n\tcount: <code@count(int[1,2,3,4,5,6,7,8,9])>";
        Translator translator = new Translator(code);
        Object value = translator.execute(template);
        System.out.println(value);
    }

    输出结果：
    result:
     	join: It's [3.14]
    	count: 45
```

### 说明
模版(template)中['<'、'>']包围的内容表示为待执行表达式, **表达式中['@'、'('、'#'、'['、']'、','、')'、'\']为转义字符**, 在参数中使用时需要通过['\']转义

'@': 用于分割酶实例名称与方法名

'#': 用与分割参数类型与参数值（仅支持基础类型）

'[' 与 ']': 表示数组值
