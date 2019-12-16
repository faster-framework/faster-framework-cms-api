<div class="h_bg">
    <div class="head clearfix">
        <div class="logo"><a href="/"><img src="/static/Assets/images/logo.png" alt=""/></a></div>
        <ul class="nav clearfix">
            <@Categories position="top-nav">
                <#list data as item>
                    <li>
                        <a href="/c/${item.code}">
                            ${item.name}<p>${item.description}</p>
                        </a>
                    </li>
                </#list>
            </@Categories>
        </ul>
        <div class="head_r">
            <div>
                <span>关注</span>
                <a href="" class="ra">&nbsp;</a>
                <a href="" class="rb">&nbsp;</a>
                <a href="" class="rc">&nbsp;</a>
            </div>
            <div>
                <span>服务热线</span>
                <span class="yellow">400-400-0000</span>
            </div>
        </div>
    </div>
</div>