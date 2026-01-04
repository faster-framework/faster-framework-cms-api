<html>

<body>

<p>
    模板上下文测试：
</p>
<p>
    当前页栏目名称：${cms.s.name}
</p>
<p>
    父栏目名称：${cms.p.name!'不存在父级'}
</p>
<p>
    父栏目列表名称：<#list cms.plist as item>${item.name}<br/></#list>
</p>
<p>
    当前页面第几页：${cms.current}
</p>
当前页面展示条数：${cms.size}
<p>
    总页数：${cms.pages}
</p>
总记录数：${cms.total}
<p>
    是否存在上一页：${cms.hasp}
</p>
<p>
    是否存在下一页：${cms.hasn}
</p>
<p>
    下一页页数：${cms.nnum}
</p>
<p>
    上一页页数：${cms.pnum}
</p>


<p>
    栏目标签分页测试：
</p>
<@section page=true pid='${cms.s.id}' size=1>
    <p>
        <#list data as item>${item.name}<br/></#list>
    </p>
</@section>

<p>
    栏目标签列表测试：
</p>
<@section list=true pid='${cms.s.id}'>
    <p>
        <#list data as item>${item.name}<br/></#list>
    </p>
</@section>

<p>
    栏目标签单条测试：
</p>
<@section>
    <p>
        ${data.name}
    </p>
</@section>

<p>
    文章分页测试：
</p>
<@article page=true>
    <p>
        <#list data as item>${item.title}<br/></#list>
    </p>
</@article>

<p>
    文章列表测试：
</p>
<@article list=true show=false scode='${cms.s.code}'>
    <p>
        <#list data as item>${item.title}<br/></#list>
    </p>
</@article>

<p>
    文章单条测试：
</p>
<@article top=true>
    <p>
        ${data.title}
    </p>
</@article>

<p>字典列表测试：</p>
<@dict list=true>
    <#list data as item>${item.dictValue}<br/></#list>
</@dict>
<p>字典分页测试：</p>
<@dict page=true size=1>
    <#list data as item>${item.dictValue}<br/></#list>
</@dict>
<p>字典单条测试：</p>
<@dict name='a' type='b' >
    ${data.dictValue}
</@dict>
</body>
</html>