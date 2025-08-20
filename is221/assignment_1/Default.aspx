<%@ Page Title="Home Page" Language="VB" MasterPageFile="~/Site.Master" AutoEventWireup="false"
    CodeFile="Default.aspx.vb" Inherits="_Default" %>

<asp:Content ID="HeaderContent" runat="server" ContentPlaceHolderID="HeadContent">
</asp:Content>
<asp:Content ID="BodyContent" runat="server" ContentPlaceHolderID="MainContent">
    <h2>
        Welcome to ASP.NET!
    </h2>
    <p>
        To learn more about ASP.NET visit <a href="http://www.asp.net" title="ASP.NET Website">www.asp.net</a>.
    </p>
    <p>
        You can also find <a href="http://go.microsoft.com/fwlink/?LinkID=152368&amp;clcid=0x409"
            title="MSDN ASP.NET Docs">documentation on ASP.NET at MSDN</a>.
    </p>
    <label>Username:</label>
                <asp:TextBox ID="txtUsername" runat="server" Width="122px" Height="22px" 
                        BorderColor="Silver" BorderStyle="Inset" BorderWidth="1px"></asp:TextBox><br /><br />
                
               <label>Password :</label>
                
                    <asp:TextBox ID="txtPassword" runat="server" TextMode="Password" Width="122px" Height="22px" 
                        BorderColor="Silver" BorderStyle="Inset" BorderWidth="1px"></asp:TextBox>
                <br />
               <br />
                    <asp:Button ID="btnlogin" runat="server" Text="Login" BorderColor="Silver" 
                        BorderStyle="Outset" BorderWidth="2px" Width="64px" 
                        CausesValidation="False" /> 
                <asp:Label ID="lblError" runat="server" Width="257px" Height="20px" 
                    ForeColor="Red"></asp:Label> 

</asp:Content>
