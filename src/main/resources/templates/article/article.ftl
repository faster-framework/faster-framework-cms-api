<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>${cms.art.title?html}</title>
</head>
<body>
<nav>
    <a href="/">首页</a>
    <#list cms.pslist as item>
        / <a href="/s/${item.code}/">${item.name?html}</a>
    </#list>
</nav>
<main>
    <article>
        <h1>${cms.art.title?html}</h1>
        <#if cms.art.publishDate??>
            <p><time>${cms.art.publishDateText!}</time></p>
        </#if>
        <#if cms.art.description??><p>${cms.art.description?html}</p></#if>
        <div class="content">${cms.art.content!}</div>
    </article>
</main>
</body>
</html>
