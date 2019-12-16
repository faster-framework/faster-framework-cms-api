<html xmlns="http://www.w3.org/1999/xhtml">

<#include "/common/header.ftl">

<body>
<#--导航-->
<#include "/common/nav.ftl">
<!--幻灯片-->
<@Category code="banner" asName="categoryData">
    <div class="banner">
        <div class="slider">
            <div class="flexslider">
                <ul class="slides">
                    <@Categories parentId=categoryData.id>
                        <#list data as item>
                            <li><a href=""><img src='${item.img}' alt=""/></a></li>
                        </#list>
                    </@Categories>
                </ul>
            </div>
        </div>
    </div>
    <!--幻灯片-->
    <div class="space_hx">&nbsp;</div>
</@Category>


<!--我们的服务-->
<@Category code="service"  asName="categoryData">
    <div class="i_ma">
        <div class="name">+<em>${categoryData.name}</em>+</div>
        <p>${categoryData.description}</p>
        <div class="space_hx">&nbsp;</div>
        <ul class="clearfix">
            <@Categories parentId=categoryData.id>
                <#list data as item>
                    <li>
                        <a href="">
                            <div class="tu"><img src="${item.img}" alt=""/></div>
                            <h6>${item.name}</h6>
                        </a>
                    </li>
                </#list>
            </@Categories>
        </ul>
    </div>
    <div class="space_hx">&nbsp;</div>
</@Category>

<@Category code="demos" asName="categoryData">
    <div class="bg_b">
        <div class="i_mb">
            <div class="name">${categoryData.name}</div>
            <div class="tabBox_t">
                <div class="tabBox">
                    <ul class="tabNav">
                        <@Categories parentId=categoryData.id>
                            <#list data as item>
                                <li <#if item_index==0>class="now"</#if>>${item.name}</li>
                            </#list>
                        </@Categories>
                    </ul>
                    <@Categories parentId=categoryData.id>
                        <#list data as item>
                            <div class="tabCont" <#if item_index==0>style="display:block;"</#if>>
                                <!--影视项目-->
                                <ul class="clearfix case_l">
                                    <@Articles size="5" categoryId=item.id asName="articleData">
                                        <#list articleData as articleItem>
                                            <li>
                                                <a href="/art/${articleItem.id}">
                                                    <div class="tu">
                                                        <img src="${articleItem.img}" alt=""/>
                                                        <div class="s_b"><img src="/static/Assets/images/icon9.png" alt=""/>
                                                        </div>
                                                    </div>
                                                    <div class="title">${articleItem.title}</div>
                                                    <div class="des">${articleItem.description}</div>
                                                </a>
                                            </li>
                                        </#list>
                                    </@Articles>
                                </ul>
                            </div>
                        </#list>
                    </@Categories>
                </div>
            </div>
        </div>
    </div>
    <div class="space_hx">&nbsp;</div>
</@Category>
<#include "/common/footer.ftl">
</body>
</html>
