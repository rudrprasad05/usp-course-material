<%@ Page Language="VB" AutoEventWireup="false" CodeFile="question.aspx.vb" Inherits="question" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <div>
            <asp:Label ID="lbQuestion" runat="server" Text="Question: "></asp:Label>
            <asp:TextBox ID="txtQestion" runat="server"></asp:TextBox>
        </div>
        <div>
            <asp:Label ID="lbAnswer" runat="server" Text="Answer: "></asp:Label>
            <asp:TextBox ID="txtAnswer" runat="server" Rows="8" TextMode="MultiLine"></asp:TextBox>
        </div>
        <div>
            <asp:Button ID="btnNew" runat="server" Text="New Question" />
            <asp:Button ID="btnClear" runat="server" Text="Clear" />
            <asp:Button ID="btnAdd" runat="server" Text="Add question" />
        </div>
    </div>
     <asp:AccessDataSource ID="addQuestion" runat="server" 
        DataFile="~/App_Data/Revision.mdb" 
        DeleteCommand="DELETE FROM [Questions] WHERE [ID] = ?" 
        InsertCommand="INSERT INTO [Questions] ([Question], [Answer]) VALUES (?, ?)" 
        SelectCommand="SELECT * FROM [Questions]" 
        UpdateCommand="UPDATE [Questions] SET [Question] = ?, [Answer] = ? WHERE [ID] = ?">
        <DeleteParameters>
            <asp:Parameter Name="ID" Type="Int32" />
        </DeleteParameters>
        <InsertParameters>
            <asp:Parameter Name="Question" Type="String" />
            <asp:Parameter Name="Answer" Type="String" />
        </InsertParameters>
        <UpdateParameters>
            <asp:Parameter Name="Question" Type="String" />
            <asp:Parameter Name="Answer" Type="String" />
            <asp:Parameter Name="ID" Type="Int32" />
        </UpdateParameters>
    </asp:AccessDataSource>
    </form>

    <asp:Label ID="lbQuestionText" runat="server" Text=""></asp:Label>
   
</body>
</html>
