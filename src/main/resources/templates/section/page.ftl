<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>${cms.s.name?html} - 第 ${cms.current} 页</title>
</head>
<body>
<nav>
    <a href="/">首页</a>
    <#list cms.plist as item>
        / <a href="/s/${item.code}/">${item.name?html}</a>
    </#list>
</nav>
<main>
    <h1>${cms.s.name?html}</h1>
    <#if cms.s.description??><p>${cms.s.description?html}</p></#if>

    <@article page=true sid=cms.s.id size=cms.size current=cms.current>
        <ul>
            <#list data as item>
                <li><a href="/a/${item.id}/">${item.title?html}</a></li>
            </#list>
        </ul>
    </@article>

    <nav>
        <#if cms.hasp><a href="/s/${cms.s.code}/${cms.pnum}/">上一页</a></#if>
        <span>第 ${cms.current} / ${cms.pages} 页（共 ${cms.total} 条）</span>
        <#if cms.hasn><a href="/s/${cms.s.code}/${cms.nnum}/">下一页</a></#if>
    </nav>
</main>
</body>
</html>
