<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Faster CMS</title>
</head>
<body>
<header>
    <nav>
        <a href="/">首页</a>
        <@section list=true pid=0>
            <#list data as item>
                <a href="/s/${item.code}/">${item.name?html}</a>
            </#list>
        </@section>
    </nav>
</header>
<main>
    <h1>最新文章</h1>
    <@article list=true size=10>
        <ul>
            <#list data as item>
                <li>
                    <a href="/a/${item.id}/">${item.title?html}</a>
                    <#if item.publishDate??>
                        <time>${item.publishDateText!}</time>
                    </#if>
                </li>
            </#list>
        </ul>
    </@article>
</main>
<footer>
    <p>Powered by Faster CMS</p>
</footer>
</body>
</html>
