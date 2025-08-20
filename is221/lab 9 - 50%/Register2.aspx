<%@ Page Title="Student Registration Form" Language="VB" MasterPageFile="~/MasterPages/University.master" AutoEventWireup="false" CodeFile="Register2.aspx.vb" Inherits="Register" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" Runat="Server">
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="contentArea" Runat="Server">
    <h2>
        Student Registration Form
    </h2>
    <table class="submitButton">
        <tr>
            <td>
                Name</td>
            <td>
                <asp:TextBox ID="txtName" runat="server"></asp:TextBox>
                <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" 
                    ErrorMessage="*Required value" ControlToValidate="txtName"></asp:RequiredFieldValidator>
            </td>
        </tr>
        <tr>
            <td>
                Student ID</td>
            <td>
                <asp:TextBox ID="txtStudentID" runat="server"></asp:TextBox>
                <asp:RequiredFieldValidator ID="RequiredFieldValidator2" runat="server" 
                    ErrorMessage="*Required value" ControlToValidate="txtStudentID"></asp:RequiredFieldValidator>
                   <asp:RegularExpressionValidator ID="RegularExpressionValidator1" ValidationExpression="s\d\d\d\d\d\d\d\d"  ControlToValidate="txtStudentID" runat="server" ErrorMessage="incorrect type"></asp:RegularExpressionValidator>
            </td>
        </tr>
        <tr>
            <td>
                Password</td>
            <td>
                <asp:TextBox ID="txtPassword" runat="server" TextMode="Password"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>
                Programme</td>
            <td>
                <asp:DropDownList ID="ddlProgram" runat="server" Height="16px">
                    <asp:ListItem>Bsc</asp:ListItem>
                    <asp:ListItem>Bcomm</asp:ListItem>
                    <asp:ListItem>BA</asp:ListItem>
                    <asp:ListItem>BEng</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>
                Majors</td>
            <td>
                <asp:CheckBoxList ID="cblMajors" runat="server" RepeatColumns="3" 
                    DataSourceID="xdsMajors" DataTextField="name" 
 >
                </asp:CheckBoxList>
                <asp:XmlDataSource ID="xdsMajors" runat="server" 
                    DataFile="~/App_Data/Majors.xml"></asp:XmlDataSource>
            </td>
        </tr>

         <tr>
            <td>
                &nbsp;
            </td>
            <td>
                <asp:Button ID="btnRegister" runat="server" Text="Register Me" />
            </td>
        </tr>
    </table>
    <asp:Label ID="lblVerify" runat="server" Text=""></asp:Label>
    <asp:AccessDataSource
        ID="adsStudents" runat="server" DataFile="~/App_Data/Students.mdb" 
        DeleteCommand="DELETE FROM [Student] WHERE (([Stu_ID] = ?) OR ([Stu_ID] IS NULL AND ? IS NULL))" 
        InsertCommand="INSERT INTO [Student] ([Stu_ID], [Stu_Name], [Stu_Passwd], [Stu_Prog]) VALUES (?, ?, ?, ?)" 
        SelectCommand="SELECT * FROM [Student]" 
        UpdateCommand="UPDATE [Student] SET [Stu_Name] = ?, [Stu_Passwd] = ?, [Stu_Prog] = ? WHERE (([Stu_ID] = ?) OR ([Stu_ID] IS NULL AND ? IS NULL))">
        <DeleteParameters>
            <asp:Parameter Name="Stu_ID" Type="String" />
        </DeleteParameters>
        <InsertParameters>
            <asp:Parameter Name="Stu_ID" Type="String" />
            <asp:Parameter Name="Stu_Name" Type="String" />
            <asp:Parameter Name="Stu_Passwd" Type="String" />
            <asp:Parameter Name="Stu_Prog" Type="String" />
        </InsertParameters>
        <UpdateParameters>
            <asp:Parameter Name="Stu_Name" Type="String" />
            <asp:Parameter Name="Stu_Passwd" Type="String" />
            <asp:Parameter Name="Stu_Prog" Type="String" />
            <asp:Parameter Name="Stu_ID" Type="String" />
        </UpdateParameters>
    </asp:AccessDataSource>
    <asp:AccessDataSource ID="adsMajors" runat="server" 
        DataFile="~/App_Data/Students.mdb" 
        DeleteCommand="DELETE FROM [StuMajors] WHERE (([Stu_Id] = ?) OR ([Stu_Id] IS NULL AND ? IS NULL)) AND (([Stu_Majors] = ?) OR ([Stu_Majors] IS NULL AND ? IS NULL))" 
        InsertCommand="INSERT INTO [StuMajors] ([Stu_Id], [Stu_Majors]) VALUES (?, ?)" 
        SelectCommand="SELECT * FROM [StuMajors]">
        <DeleteParameters>
            <asp:Parameter Name="Stu_Id" Type="String" />
            <asp:Parameter Name="Stu_Majors" Type="String" />
        </DeleteParameters>
        <InsertParameters>
            <asp:Parameter Name="Stu_Id" Type="String" />
            <asp:Parameter Name="Stu_Majors" Type="String" />
        </InsertParameters>
    </asp:AccessDataSource>
</asp:Content>


