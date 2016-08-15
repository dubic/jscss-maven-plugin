package com.dubic.jscss.maven.plugin;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import com.dubic.jscss.maven.plugin.xml.bean.Aggregation;
import com.dubic.jscss.maven.plugin.xml.bean.Webresource;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXB;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.FileUtils;

/**
 * Goal which touches a timestamp file.
 *
 *
 */
@Mojo(name = "aggregate")
public class AggregatePlugin extends AbstractMojo {

    String PROJECT_PATH = new File("test.file").getAbsoluteFile().getParent();
    String RESOURCE_PATH = PROJECT_PATH + "/src/main/resources";
//    private String PROFILE_DEV = "development";
//    String SCRIPTS_PATH = RESOURCE_PATH + "/static/scripts";
//    String STYLES_PATH = RESOURCE_PATH + "/static/styles";

    /**
     * Location of the file.
     *
     * @throws org.apache.maven.plugin.MojoExecutionException
     */
    @Parameter(required = true)
    private boolean production;
    @Parameter(required = true)
    private String classesDir;
    @Parameter(required = true)
    private String webresourcesDir;
    long version = System.currentTimeMillis();

    public void execute() throws MojoExecutionException {

        if (!production) {
            getLog().info("BASE DIR : " + classesDir);
            getLog().info("Production property set to false. Ignoring aggregation plugin");
            return;
        }
        try {
//            read all xml files from webresources folder
            for (File wrXmlfile : new File(webresourcesDir).listFiles()) {
                if (wrXmlfile.isDirectory()) {
                    continue;
                }
                processWebResource(wrXmlfile);
            }

        } catch (IOException ex) {
            throw new MojoExecutionException("Cannot read the web-resource.xml in " + RESOURCE_PATH, ex);
        }

    }

    public void processWebResource(File wrXmlfile) throws IOException {
        getLog().info("reading : "+wrXmlfile.getName());

        Webresource webresource = JAXB.unmarshal(new FileReader(wrXmlfile), Webresource.class);
//            LIST AGGREGATIONS
        List<Aggregation> aggregations = webresource.getAggregations();
        Map<String, String> templateMap = new HashMap<String, String>();

        for (Aggregation aggregation : aggregations) {
            String aggregatedFileName = String.format(aggregation.getAggregatedFileName(), "-" + this.version);

            String writePath = classesDir + File.separator + aggregatedFileName;

            getLog().info("created aggregated file : " + writePath);
            getLog().info("Loading " + aggregation.getFilePath().size() + " files for aggregation : " + aggregation.getAggregatedFileName());
            for (String resourcePath : aggregation.getFilePath()) {
                getLog().info("Aggregating : " + resourcePath);
                String contents = FileUtils.fileRead(RESOURCE_PATH + File.separator + resourcePath);
                FileUtils.fileAppend(writePath, contents);

                FileUtils.fileAppend(writePath, "\n/* -----------------END---------------------*/\n");
                templateMap.put(aggregation.getName(), new File(writePath).getName());
            }
        }
//            RECREATE HTML
        String prodPageTemplate = FileUtils.fileRead(RESOURCE_PATH + File.separator + webresource.getTemplate());

        StrSubstitutor sub = new StrSubstitutor(templateMap);
        String prodHtml = sub.replace(prodPageTemplate);

        FileUtils.fileWrite(classesDir + File.separator + webresource.getPage(), prodHtml);
    }
}
