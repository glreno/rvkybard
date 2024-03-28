<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="com.rfacad.rvkybard.KybardJspHelper" %>
<%
    KybardJspHelper kb=new KybardJspHelper(out,"Star Raiders",11*3+3,5*3+2,null);
    String SHIFTCOLOR="#D68554";
    String CTRLCOLOR="#F9C05D";

    // Default key SVG and size
    String D1="atari/keys/";
    String D2="starRaiders/keys/";
    kb.setDefaultSvg(D1+"key.svgt",3,3,"FS=48","FS2=16","BORD=4","BORDC=#F5F0EA","BGC=#736C55","TXTC=#F5F0EA","SHFBGC="+SHIFTCOLOR,"CTLBGC="+CTRLCOLOR,"FIXSY=0","FIXLY=0");
    kb.startHtml();
%>
<style>
.kybard-container {
    background-color: rgb(72,75,77);
};
</style>
<script type="text/javascript" language="javascript">
    // custom javascript goes here
</script>
</head>
<body>
<%
    kb.startKeyboard();
    //
    // Keyboard rows start here
    //
    kb.startRow();
    kb.endRowThirds(1);

    // Top Row
    kb.startRow();
    kb.spacer(1);
    kb.key("1","KB_1");
    kb.key("2","KB_2");
    kb.key("3","KB_3");
    kb.key("4","KB_4");
    kb.key("5","KB_5");
    kb.key("6","KB_6");
    kb.key("7","KB_7");
    kb.key("8","KB_8");
    kb.key("9","KB_9");
    kb.spacer(3);
    kb.key("0","KB_0",4,3,null,null,"",null,"BGC="+SHIFTCOLOR);
    kb.endRow();

    // F C T G H Option
    kb.startRow();
    kb.spacer(1);
    kb.key("F","KB_F",6,6,null,null,"",D2+"keyF.svgt","S1=Forward","S2=View");
    kb.key("C","KB_C",6,6,null,null,"",D2+"keyC.svgt","S1=Attack","S2=Computer");
    kb.key("T","KB_T",6,6,null,null,"",D2+"key.svgt","S1=Targeting","S2=Computer");
    kb.key("G","KB_G",6,6,null,null,"",D2+"keyG.svgt","S1=Galactic","S2=Chart");
    kb.key("H","KB_H",6,6,null,null,"",D2+"key.svgt","S1=Hyperwarp","S2=");
    kb.key("OPTION","KB_F4",4,3,null,null,"",null,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
    kb.endRow();

    // Select
    kb.startRow();
    kb.spacer(31);
    kb.key("SELECT","KB_F3",4,3,null,null,"",null,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
    kb.endRow();

    // A S M L   Start
    kb.startRow();
    kb.spacer(1);
    kb.key("A","KB_A",6,6,null,null,"",D2+"keyA.svgt","S1=Aft","S2=View");
    kb.key("S","KB_S",6,6,null,null,"",D2+"keyS.svgt","S1=Shields","S2=");
    kb.key("M","KB_M",6,6,null,null,"",D2+"key.svgt","S1=Manual Target","S2=Select");
    kb.key("L","KB_L",6,6,null,null,"",D2+"keyL.svgt","S1=Long Range","S2=Scanner");
    kb.key(" ","KB_SPACE",6,6,"panic()","panic()","",null);
    kb.key("START","F2",4,3,null,null,"",null,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
    kb.endRow();

    // P
    kb.startRow();
    kb.spacer(31);
    kb.key("P","KB_P",4,3,null,null,"",D2+"keyPause.svgt","S2=PAUSE");
    kb.endRow();

    //
    // Keyboard is finished
    //
    kb.endKeyboard();
    kb.endHtml();
%>
