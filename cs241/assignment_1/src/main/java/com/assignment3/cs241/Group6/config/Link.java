package com.assignment3.cs241.Group6.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

    public class Link {
        private String url;
        private String label;

        public Link(String url, String label) {
            this.url = url;
            this.label = label;
        }

        public String getUrl() {
            return url;
        }

        public String getLabel() {
            return label;
        }
    }

