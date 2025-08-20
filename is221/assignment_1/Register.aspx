<%@ Page Title="" Language="VB" MasterPageFile="~/Auth.master" AutoEventWireup="false" CodeFile="Register.aspx.vb" Inherits="Register" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">

<form id="form1">
        <a class="backhome" href="Home.aspx">&#8676 &nbsp Back Home</a>
        <section class="login-cont">
        
            <aside>
                <img src="images/login_bg.jpg" alt="login image" />
            </aside>
            <main class="login-inputform">
                <h1>Register</h1>
                <p>Already have an account? <a href="Login.aspx">Login now</a></p>

                <section>
                    <section>
                        <asp:Label ID="loginUserNameLabel" runat="server" Text="Username"></asp:Label>
                        <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" ErrorMessage="*This field is required" ControlToValidate="txtUsername" CssClass="danger"></asp:RequiredFieldValidator>
                    </section>            
                    <asp:TextBox ID="txtUsername" runat="server"></asp:TextBox> 
                </section>
                
                <section>
                    <section>
                        <asp:Label ID="loginPasswordLabel" runat="server" Text="Password"></asp:Label>
                        <asp:RequiredFieldValidator ID="RequiredFieldValidator2" runat="server" ErrorMessage="*This field is required" ControlToValidate="txtPassword" CssClass="danger"></asp:RequiredFieldValidator>
                    </section>
                    
                    <asp:TextBox ID="txtPassword" TextMode="Password" runat="server"></asp:TextBox>
                    
                </section>

                <section>
                    <asp:Button class="call-to-action-btn" ID="btnAddUser" runat="server" Text="Register"></asp:Button>
                    <asp:Label ID="Label1" runat="server"></asp:Label>
                </section>
                
            </main>
  
        </section>
     </form>

    <asp:Label ID="lbQuestionText" runat="server" Text=""></asp:Label>

    <asp:AccessDataSource ID="addUser" runat="server" 
        DataFile="~/App_Data/user1.mdb" 
        DeleteCommand="DELETE FROM [User] WHERE [User_ID] = ?" 
        InsertCommand="INSERT INTO [User] ([User_Name], [User_Passwd], [User_Prog]) VALUES (?, ?, ?)" 
        SelectCommand="SELECT * FROM [User]" 
        UpdateCommand="UPDATE [User] SET [User_Name] = ?, [User_Passwd] = ?, [User_Prog] = ? WHERE [User_ID] = ?">
        <DeleteParameters>
            <asp:Parameter Name="User_ID" Type="Int32" />
        </DeleteParameters>
        <InsertParameters>
            <asp:Parameter Name="User_Name" Type="String" />
            <asp:Parameter Name="User_Passwd" Type="String" />
            <asp:Parameter Name="User_Prog" Type="String" />
        </InsertParameters>
        <UpdateParameters>
            <asp:Parameter Name="User_Name" Type="String" />
            <asp:Parameter Name="User_Passwd" Type="String" />
            <asp:Parameter Name="User_Prog" Type="String" />
            <asp:Parameter Name="User_ID" Type="Int32" />
        </UpdateParameters>
    </asp:AccessDataSource>

    <script>
        document.getElementById("MainContent_btnAddUser").addEventListener("click", saveCookieLogin)
        let username = document.getElementById("MainContent_txtUsername").value
        let password = document.getElementById("MainContent_txtPassword").value
        let pwdRegex = /[A-Z]{6,32}/
        function saveCookieLogin() {
            console.log("l")
            localStorage.setItem('credentials', true)
        }  
    </script>
</asp:Content>

