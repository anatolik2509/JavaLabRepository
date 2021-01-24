import com.google.auto.service.AutoService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes(value = {"HtmlForm", "HtmlInput"})
@Slf4j
public class HtmlProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(HtmlForm.class);
        for(Element element : annotatedElements){
            String path = HtmlProcessor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            path = path.substring(1) + element.getSimpleName().toString() + ".html";
            Path out = Paths.get(path);
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(out.toFile()));
                HtmlForm annotation = element.getAnnotation(HtmlForm.class);
                writer.write("<form action='" + annotation.action() + "' method='" + annotation.method() + "'>\n");
                log.info("types:" + element.getEnclosedElements());
                List<? extends Element> elements = element.getEnclosedElements();
                for(Element e : elements){
                    HtmlInput input = e.getAnnotation(HtmlInput.class);
                    if(input != null){
                        writer.write(String.format("\t<input name='%s' type='%s' placeholder='%s'/>\n",
                                input.name(), input.type(), input.placeholder()));
                    }
                }
                writer.write("</form>");
                writer.close();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }


        }
        return true;
    }
}
