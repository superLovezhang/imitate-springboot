package com.tyzz.mvc.config.core.engine;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.ServletLoader;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class ViewEngine {
    private PebbleEngine engine;

    public ViewEngine(ServletContext context) {
        ServletLoader loader = new ServletLoader(context);
        loader.setCharset("UTF-8");
        loader.setPrefix("/WEB-INF/templates");
        loader.setSuffix(".html");
        this.engine = new PebbleEngine.Builder()
                .autoEscaping(true)
                .cacheActive(false)
                .loader(loader)
                .build();
    }

    public void render(ModelAndView mv, PrintWriter writer) throws IOException {
        Map<String, Object> model = mv.getData();
        String view = mv.getView();
        PebbleTemplate template = engine.getTemplate(view);
        template.evaluate(writer, model);
    }
}
