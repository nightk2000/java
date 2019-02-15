在编写PC网页的时候，不可避免的会遇到手机访问的情况
这个时候，页面中无法使用百分百宽度或高度的内容，很有可能会被撑爆
在这种情况下，使用响应式布局样式，就能有效的解决这种尴尬

```
<style type="text/css">
/** 页面全局div样式 **/
div{width:200px;line-height:40px;color:#ffffff}

/** 实例1，以设备屏幕最大尺寸为基准，当最大宽度为640时，div的样式就会以宽度100、行高20来显示，颜色则继承上面的全局白色 **/
@media screen and (max-device-width:640px){
	div{width:100px;line-height:20px;}
}
/** 实例2，以浏览器最大宽度为基准，当最大宽度为640时，div的样式就会以宽度100、行高20来显示，颜色则继承上面的全局白色 **/
@media screen and (max-width:640px){
	div{width:100px;line-height:20px;}
}
/** 实例3，以浏览器最大宽度为基准，当最小宽度为640、且最大宽度为800时，div的样式就会以宽度100、行高20来显示，颜色则继承上面的全局白色 **/
@media screen and (min-width:640px) and (max-width:800px){
	div{width:100px;line-height:20px;}
}

/**
max-device-width、max-width，都有与之对应的min-device-width、min-width，下文有详细讲解

参考文档：http://www.w3school.com.cn/html5/att_a_media.asp

@media [term] [MediaType] and (...)
term：条件，取值为not或only，默认为only
    not：规定 NOT 运算符
    only：仅
    and：规定 AND 运算符
    ,：（半角逗号）规定 OR 运算符
MediaType：设备类型
    all：所有设备
    aural：听觉设备
    braille：点字触觉设备
    handled：便携设备，如手机、平板电脑
    print：打印预览图等
    projection：投影设备
    screen：显示器、笔记本、移动端等设备，常用项
    tty：如打字机或终端等设备
    tv：电视机等设备类型
    embossed：盲文打印机
(...)：条件属性类型
    width：规定目标显示区域的宽度，可使用 "min-" 和 "max-" 前缀
    height：规定目标显示区域的高度，可使用 "min-" 和 "max-" 前缀
    device-width：规定目标显示器/纸张的宽度，可使用 "min-" 和 "max-" 前缀
    device-height：规定目标显示器/纸张的高度，可使用 "min-" 和 "max-" 前缀
    orientation：规定目标显示器/纸张的取向，可能的值："portrait" 或 "landscape"，例子：media="all and (orientation: landscape)"
    aspect-ratio：规定目标显示区域的宽度/高度比，可使用 "min-" 和 "max-" 前缀，例子：media="screen and (aspect-ratio:16/9)"
    device-aspect-ratio：规定目标显示器/纸张的 device-width/device-height 比率，可使用 "min-" 和 "max-" 前缀，例子：media="screen and (aspect-ratio:16/9)"
    color：规定目标显示器的 bits per color，可使用 "min-" 和 "max-" 前缀，例子：media="screen and (color:3)"
    monochrome：规定在单色帧缓冲中的每像素比特，可使用 "min-" 和 "max-" 前缀，例子：media="screen and (monochrome:2)"
    resolution：规定目标显示器/纸张的像素密度 (dpi or dpcm)，可使用 "min-" 和 "max-" 前缀，例子：media="print and (resolution:300dpi)"
    scan：规定 tv 显示器的扫描方法，可能的值是："progressive" 和 "interlace"，例子：media="tv and (scan:interlace)"
    grid：规定输出设备是网格还是位图，可能的值："1" 代表网格，"0" 是其他，例子：media="handheld and (grid:1)"

例：
@media screen                仅限显示器、笔记本、移动端等设备
@media only screen           仅限显示器、笔记本、移动端等设备
@media not screen            非显示器、笔记本、移动端等设备
@media                       所有设备
@media all                   所有设备
@media screen and (min-width:640px) and (max-width:800px)
                             仅限显示器、笔记本、移动端等设备，且最小显示宽度为640、最大显示宽度为800的设备
@media screen and (max-width:640px)
                             仅限显示器、笔记本、移动端等设备，且最大显示宽度为640的设备

如何使用：
1、可直接定义样式使用：
@media screen and (max-width:640px){
    span{line-height:20px}
}
2、在<a>标签中使用（这个主要用于非screen，没测试过，不知道具体效果）
<a href="url" media="print and (resolution:300dpi)">link</a>
**/
</style>
```
