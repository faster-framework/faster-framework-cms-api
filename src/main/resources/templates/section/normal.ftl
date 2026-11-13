<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>${cms.s.name?html}</title>
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
    <#if cms.s.content??><div>${cms.s.content}</div></#if>

    <@article list=true sid=cms.s.id size=20>
        <ul>
            <#list data as item>
                <li><a href="/a/${item.id}/">${item.title?html}</a></li>
            </#list>
        </ul>
    </@article>
</main>
</body>
</html>
