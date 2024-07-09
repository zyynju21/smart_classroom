package com.yiyue.common.util;

import org.springframework.core.io.ClassPathResource;

import java.io.*;

public class FileUtils {

    public static void tpl2HtmlFile(String tplName,String outPath,String outFileName,String userId,String aid) throws IOException {
        /*Resource resource = new ClassPathResource(tplName);
        File file = resource.getFile();
        BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file)));*/

        //InputStream inputStream = this.getClass().getResourceAsStream(tplName);
        InputStream inputStream = new ClassPathResource(tplName).getInputStream();
        BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
        File dir=new File(outPath);
        if (!dir.exists()){
            dir.mkdirs();
        }
        File outFile=new File(outPath+"/"+outFileName);
        BufferedWriter out=new BufferedWriter(new FileWriter(outFile));
        String str=null;
        while ((str=br.readLine())!=null){
            if(str.indexOf("yiyue-data:userId")!=-1){
                str=str.replace("yiyue-data:userId",userId);
            }
            if(str.indexOf("yiyue-data:aid")!=-1){
                str=str.replace("yiyue-data:aid",aid);
            }
            out.write(str);
            out.newLine();
        }
        out.flush();
        out.close();
    }
}
