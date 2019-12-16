<html>
<#include "/common/header.ftl">
<body>
<#include "/common/nav.ftl">
<@ArticlePage current=page.current categoryCode=page.categoryCode size=page.size>
    <#list data.records as item>
        <div>标题：${item.title}</div>
        <div>内容：${item.content}</div>
    </#list>
</@ArticlePage>
<#include "/common/footer.ftl">
</body>
</html>