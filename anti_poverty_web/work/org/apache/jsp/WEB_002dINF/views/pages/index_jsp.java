package org.apache.jsp.WEB_002dINF.views.pages;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.authentication.AttributePrincipal;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("  \n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("<meta name=\"decorator\" content=\"default\" />\n");
      out.write("\n");
      out.write("<script type=\"text/javascript\"> \n");
      out.write(" \t \n");
      out.write("\t //resize ================================\n");
      out.write("\t function resize(){\n");
      out.write("\t\t document.getElementById(\"mainPanel\").style.height = $(window).height()-50;\n");
      out.write("\t\t $('#mainPanel').layout(\"resize\");\n");
      out.write(" \t }\n");
      out.write("\t \n");
      out.write("\t $(function(){\n");
      out.write("\t\t\tCommon.init();\n");
      out.write("\t\t\tinitMenus();\n");
      out.write(" \t});\n");
      out.write("\t \n");
      out.write("\t window.onload = window.onresize = function(){\n");
      out.write(" \t\tresize();\n");
      out.write("\t\t//Page.utils.autoResize();\n");
      out.write("\t };\n");
      out.write("\t \n");
      out.write("\t function initMenus(){\n");
      out.write(" \t\t    //$(\".easyui-accordion\").append(menulist);\n");
      out.write(" \t\t\t$('.easyui-accordion li a').click(function(){\n");
      out.write("\t\t\t\tvar tabTitle = $(this).text();\n");
      out.write("\t\t\t\tvar url = $(this).attr(\"href\");\n");
      out.write("\t\t\t\taddTab(tabTitle,url);\n");
      out.write("\t\t\t\t$('.easyui-accordion li div').removeClass(\"selected\");\n");
      out.write("\t\t\t\t$(this).parent().addClass(\"selected\");\n");
      out.write("\t\t\t}).hover(function(){\n");
      out.write("\t\t\t\t$(this).parent().addClass(\"hover\");\n");
      out.write("\t\t\t},function(){\n");
      out.write("\t\t\t\t$(this).parent().removeClass(\"hover\");\n");
      out.write("\t\t\t});\n");
      out.write(" \t\t\t$(\".easyui-accordion\").accordion();\n");
      out.write(" \t }\n");
      out.write("\t \n");
      out.write(" \t function addTab(subtitle,url){\n");
      out.write("\t \tif(!$('#tabs').tabs('exists',subtitle)){\n");
      out.write("\t \t\t$('#tabs').tabs('add',{\n");
      out.write("\t \t\t\ttitle:subtitle,\n");
      out.write("\t \t\t\tcontent:createFrame(url),\n");
      out.write("\t \t\t\tclosable:true,\n");
      out.write("\t \t\t\twidth:$('#mainPanle').width()-10,\n");
      out.write("\t \t\t\theight:$('#mainPanle').height()-26\n");
      out.write("\t \t\t});\n");
      out.write("\t \t}else{\n");
      out.write("\t \t\t$('#tabs').tabs('select',subtitle);\n");
      out.write("\t \t}\n");
      out.write("\t \ttabClose();\n");
      out.write("\t }\n");
      out.write("\n");
      out.write("\t function createFrame(url)\n");
      out.write("\t {\n");
      out.write("\t \tvar s = '<iframe name=\"mainFrame\" scrolling=\"auto\" frameborder=\"0\"  src=\"'+url+'\" style=\"width:100%;height:100%;\"></iframe>';\n");
      out.write("\t \treturn s;\n");
      out.write("\t }\n");
      out.write("\t \n");
      out.write("</script>\n");
      out.write("\n");
      out.write("</head>\n");
      out.write("<body   >\n");
      out.write(" \n");
      out.write("<div id=\"mainPanel\"  class=\"easyui-layout\"  style=\"width:100%;height:500px;\" >\n");
      out.write("\n");
      out.write("<div region=\"south\" split=\"true\" border=\"false\"  style=\"height: 26px;overflow:hidden;\">\n");
      out.write("\t<div class=\"footer\" style=\"background: #eee;text-align:center\">åæå¬å¸çæææ.</div>\n");
      out.write("</div>\n");
      out.write("\t\n");
      out.write("<div region=\"west\" split=\"true\" title=\"å¯¼èªèå\" style=\"width:180px;\" id=\"west\">\n");
      out.write("\t<div class=\"easyui-accordion\" fit=\"true\" border=\"false\">\n");
      out.write("    <!--  å¯¼èªåå®¹ -->\n");
      out.write("      <div title=\"ç¨æ·ç®¡ç\"   style=\"padding:0px;overflow:auto;\">\n");
      out.write("          <div >\n");
      out.write("             <ul >\n");
      out.write("                 <li>\n");
      out.write("                     <div>\n");
      out.write("                 \t     <a target=\"mainFrame\"   href=\"/birp_web/highcharts.jsp\"><span class=\"icon icon-add\"></span>æ·»å ç¨æ·ä¿¡æ¯</a>\n");
      out.write("                 \t</div>\n");
      out.write("                 </li>\n");
      out.write("                 \n");
      out.write("                 <li>\n");
      out.write("                   <div>\n");
      out.write("                 \t<a href=\"#\" ><span class=\"icon icon-users\"></span>ç¨æ·ä¿¡æ¯ç®¡ç</a>\n");
      out.write("                 \t</div>\n");
      out.write("                 </li>\n");
      out.write("                 \n");
      out.write("                  <li>\n");
      out.write("                   <div>\n");
      out.write("                 \t<a href=\"#\" ><span class=\"icon icon-users\"></span>è§è²ä¿¡æ¯ç®¡ç</a>\n");
      out.write("                 \t</div>\n");
      out.write("                 </li>\n");
      out.write("                 \n");
      out.write("             </ul>\n");
      out.write("             </div>\n");
      out.write("      </div>\n");
      out.write("      \n");
      out.write("      <div title=\"ç³»ç»ç®¡ç\" style=\"padding:10px;\">\n");
      out.write("        content1\n");
      out.write("      </div>\n");
      out.write("    \n");
      out.write(" \t</div>\n");
      out.write("</div>\n");
      out.write("\t \n");
      out.write("<div id=\"mainPanle\" region=\"center\" style=\" overflow-y:hidden\">\n");
      out.write("        <div id=\"tabs\" class=\"easyui-tabs\"  fit=\"true\" border=\"false\" >\n");
      out.write("\t\t\t<div title=\"æ¬¢è¿ä½¿ç¨\" style=\"padding:20px;overflow:hidden;\" id=\"home\">\n");
      out.write("\t\t\t\tadmin æ¬¢è¿æ¨ç»éã\n");
      out.write(" \t\t\t</div>\n");
      out.write("\t\t</div>\n");
      out.write("</div>\n");
      out.write("    \n");
      out.write("     \n");
      out.write("\n");
      out.write("</div>\n");
      out.write("\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else log(t.getMessage(), t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
