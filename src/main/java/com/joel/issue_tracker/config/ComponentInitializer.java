package com.joel.issue_tracker.config;

import com.joel.issue_tracker.models.ComponentModel;
import com.joel.issue_tracker.repo.ComponentRepo;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComponentInitializer implements CommandLineRunner {

    @Autowired
    private ComponentRepo componentRepo;

    @Override
    public void run(String @NonNull ... args) throws Exception {

        if(componentRepo.count() > 0){
            return;
        }

        List<ComponentModel> components = List.of(

                ComponentModel.builder()
                        .componentName("Authentication")
                        .componentId("COMPONENT1001")
                        .componentDescription("Authentication and authorization issues")
                        .bugTemplate("""
                            Steps to Reproduce:

                            Expected Result:

                            Actual Result:
                            """)
                        .build(),

                ComponentModel.builder()
                        .componentName("Frontend")
                        .componentId("COMPONENT1002")
                        .componentDescription("UI related issues")
                        .bugTemplate("""
                            Page URL:

                            Browser:

                            Steps to Reproduce:

                            Expected Result:

                            Actual Result:
                            """)
                        .build(),

                ComponentModel.builder()
                        .componentName("Database")
                        .componentId("COMPONENT1003")
                        .componentDescription("Database related issues")
                        .bugTemplate("""
                            Query:

                            Error Message:

                            Expected Result:

                            Actual Result:
                            """)
                        .build()
        );

        componentRepo.saveAll(components);

    }
}
