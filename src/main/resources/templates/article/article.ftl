<html>

<body>

模板上下文测试：

当前页文章名称：${cms.art.name}
父栏目名称：${cms.ps.name!'不存在父级'}
父栏目列表名称：<#list cms.pslist as item>${item.name}<br/></#list>

</body>
</html>