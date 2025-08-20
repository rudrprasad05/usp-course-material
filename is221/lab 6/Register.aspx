<%@ Page Title="Student Registration Form" Language="VB" MasterPageFile="~/MasterPages/University.master" AutoEventWireup="false" CodeFile="Register.aspx.vb" Inherits="Register" %>

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
            </td>
        </tr>
        <tr>
            <td>
                Student ID</td>
            <td>
                <asp:TextBox ID="txtStudentID" runat="server"></asp:TextBox>
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
 >
                    <asp:ListItem>Computing Science</asp:ListItem>
                    <asp:ListItem>Information Systems</asp:ListItem>
                    <asp:ListItem>Accounting</asp:ListItem>
                    <asp:ListItem>Economics</asp:ListItem>
                    <asp:ListItem>Mathematics</asp:ListItem>
                    <asp:ListItem>Electrical Engineering</asp:ListItem>
                    <asp:ListItem>Management Studies</asp:ListItem>
                </asp:CheckBoxList>
            </td>
        </tr>

         <tr>
            <td>
                &nbsp;
            <td>
                <asp:Button ID="btnRegister" runat="server" Text="Register Me" />
            </td>
        </tr>
    </table>
</asp:Content>

