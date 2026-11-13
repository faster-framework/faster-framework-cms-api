package cn.org.faster.cms.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TemplateNamesTest {

    @Test
    void convertsBetweenViewAndTemplateNames() {
        assertThat(TemplateNames.viewName("section/page.ftl")).isEqualTo("section/page");
        assertThat(TemplateNames.viewName("/section/page.ftl")).isEqualTo("section/page");
        assertThat(TemplateNames.templateName("section/page")).isEqualTo("section/page.ftl");
        assertThat(TemplateNames.templateName("section/page.ftl")).isEqualTo("section/page.ftl");
    }

    @Test
    void rejectsTraversal() {
        assertThatThrownBy(() -> TemplateNames.viewName("../etc/passwd"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> TemplateNames.templateName(""))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
