<html>
<#include "/common/header.ftl">
<body>
<#include "/common/nav.ftl">
<@Article id=page.id>
        <div>内容：${data.content}</div>
</@Article>
<#include "/common/footer.ftl">
</body>
</html>