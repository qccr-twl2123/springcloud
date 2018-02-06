### PDF模版生成文件

```text
1.注意上传中文字体
2.具体应用场景:电子合同,电子票据
```
* maven
```xml
<!-- https://mvnrepository.com/artifact/com.itextpdf/itextpdf -->
<dependency>
    <groupId>com.itextpdf</groupId>
    <artifactId>itextpdf</artifactId>
    <version>5.5.10</version>
</dependency>
```
* 代码实现
```java
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class Snippet {
	// 利用模板生成pdf
	public static void fillTemplate() {
		// 模板路径
		String templatePath = "E:/测试3.pdf";
		// 生成的新文件路径
		String newPDFPath = "E:/ceshi.pdf";
		PdfReader reader;
		FileOutputStream out;
		ByteArrayOutputStream bos;
		PdfStamper stamper;
		try {
			out = new FileOutputStream(newPDFPath);// 输出流
			reader = new PdfReader(templatePath);// 读取pdf模板
			bos = new ByteArrayOutputStream();
			stamper = new PdfStamper(reader, bos);
			AcroFields form = stamper.getAcroFields();
			BaseFont bf = BaseFont.createFont("font/simsun/simsun.ttc,1",
            					BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            form.addSubstitutionFont(bf);

			String[] str = { "123456789", "TOP__ONE", "男", "1991-01-01", "130222111133338888", "河北省保定市" };
			int i = 0;
			java.util.Iterator<String> it = form.getFields().keySet().iterator();
			while (it.hasNext()) {
				String name = it.next().toString();
				System.out.println(name);
				form.setField(name, str[i++]);
			}
			stamper.setFormFlattening(true);// 如果为false那么生成的PDF文件还能编辑，一定要设为true
			stamper.close();

			Document doc = new Document();
			PdfCopy copy = new PdfCopy(doc, out);
			doc.open();
			PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
			copy.addPage(importPage);
			doc.close();

		} catch (IOException e) {
			System.out.println(1);
		} catch (DocumentException e) {
			System.out.println(2);
		}

	}

	public static void main(String[] args) {
		fillTemplate();
	}
}

```

* pdf工具类封装使用
```java
public class PdfUtils {
	
	private static Logger log = LoggerFactory.getLogger(PdfUtils.class);
	
	public static void fillPdfTemplate(String templatePath, String descPdfPath,
			Object data) throws Exception {
		log.info("填写合同信息实体 templatePath:{} descPdfPath:{}",templatePath,descPdfPath);
		log.info("填写合同信息实体 data:{}", JSON.toJSONString(data));
		PdfReader reader = null;
		FileOutputStream out = null;
		ByteArrayOutputStream bos = null;
		PdfStamper stamper;
		try {
			out = new FileOutputStream(descPdfPath);//输出流
			reader = new PdfReader(templatePath);//读取pdf模板
			bos = new ByteArrayOutputStream();
			stamper = new PdfStamper(reader, bos);
			AcroFields form = stamper.getAcroFields();
			BaseFont bf = BaseFont.createFont("font/simsun/simsun.ttc,1",
					BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			form.addSubstitutionFont(bf);

			List<Field> fieldList = new ArrayList<Field>();
			Class<?> cls = data.getClass();
			Class<?> superCls = cls.getSuperclass();
			fieldList.addAll(Arrays.asList(cls.getDeclaredFields()));
			fieldList.addAll(Arrays.asList(superCls.getDeclaredFields()));
			java.util.Iterator<String> it = form.getFields().keySet().iterator();
		    while(it.hasNext()){
		    	String name = it.next().toString();
				for(Field field:fieldList) {
					String fieldName = field.getName();
					if(name.equals(fieldName)){
						field.setAccessible(true);
						log.info(fieldName+" :"+ field.get(data));
				    	form.setField(name, (String)field.get(data));
					}
				}					
		    }
			//如果为false那么生成的PDF文件还能编辑，一定要设为true
		    stamper.setFormFlattening(true);
		    stamper.close();
		    out.write(bos.toByteArray());

		}catch(Exception e) {
			throw e;
		}finally{
			out.close();
			bos.close();
			reader.close();
		}
	}
}
```

* 参考博客
```text
http://blog.csdn.net/top__one/article/details/65442390

http://www.cnblogs.com/changhai/p/7248721.html
````
