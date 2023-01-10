package com.mdl.java.javadoc;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.mdl.java.javadoc.domain.FieldEntry;
import com.mdl.java.javadoc.domain.ModelClassDocVO;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.RootDoc;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import com.sun.tools.javadoc.Main;

import java.util.List;

/**
 * @description:
 * @author: meidanlong
 * @date: 2023/1/9 15:03
 */
public class Doclet {

    public static Logger logger = LoggerFactory.getLogger(Doclet.class);

    private static RootDoc rootDoc;
    private String javaBeanFilePath;

    public static boolean start(RootDoc root) {
        rootDoc = root;
        return true;
    }

    public Doclet(String javaBeanFilePath) {
        this.javaBeanFilePath = javaBeanFilePath;
    }

    public ModelClassDocVO exec() {
        ModelClassDocVO modelClassDocVO = new ModelClassDocVO();
        Main.execute(new String[]{"-doclet", Doclet.class.getName(), "-docletpath",
                Doclet.class.getResource("/").getPath(), "-encoding", "utf-8", javaBeanFilePath});
        ClassDoc[] classes = rootDoc.classes();

        if (classes == null || classes.length == 0) {
            logger.warn(javaBeanFilePath + " 无ClassDoc信息");
            return modelClassDocVO;
        }

        List<FieldEntry> entrys = Lists.newArrayList();
        ClassDoc classDoc = classes[0];






        // 获取类的名称
        modelClassDocVO.setModelClassName(classDoc.name());
        // 获取类的注释
        String classComment = ReflectUtil.getFieldValue(classDoc, "documentation").toString();
        String spitStr = "\n";
        for (String msg : classComment.split(spitStr)) {
            if (/*!msg.trim().startsWith("@") && */msg.trim().length() > 0) {
                modelClassDocVO.setModelCommentText(msg);
                break;
            }
        }
        // 获取属性名称和注释
        FieldDoc[] fields = classDoc.fields(false);
        for (FieldDoc field : fields) {
            entrys.add(new FieldEntry(field.name(), field.type().typeName(), field.commentText()));
        }

        modelClassDocVO.setFildEntryList(entrys);

        // 获取方法和注释
        MethodDoc[] methods = classDoc.methods(false);
        for (MethodDoc method : methods) {
            String methodName = method.name();
            String commonText = method.commentText();
            String returnType = method.returnType().typeName();
            Parameter[] parameters = method.parameters();
            for(Parameter parameter : parameters){
                entrys.add(new FieldEntry(methodName, parameter.type().typeName(), parameter.name()));

            }
        }
        return modelClassDocVO;
    }

    // 测试一下
    public static void main(String[] args) {
        Doclet doclet = new Doclet(
                "/Users/meidanlong/Documents/meidl/workspace/others/all-in-one/java/src/main/java/com/mdl/java/javadoc/domain/MethodDetailDTO.java");
        ModelClassDocVO modelClassDocVO = doclet.exec();
        System.out.println("类注释：" + modelClassDocVO.getModelCommentText());
        System.out.println("属性字段注释如下：");
        modelClassDocVO.getFildEntryList().forEach(System.out::println);
    }
}
