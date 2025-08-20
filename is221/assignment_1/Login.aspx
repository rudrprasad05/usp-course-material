<%@ Page Title="" Language="VB" MasterPageFile="~/Auth.master" AutoEventWireup="false" CodeFile="Login.aspx.vb" Inherits="Login" %>

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
                <h1>Sign In</h1>
                <p>Dont have an account? <a href="Register.aspx">Register now</a></p>

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
                    <asp:TextBox ID="txtPassword" runat="server" TextMode="Password"></asp:TextBox>  
                </section>

                    
                 <asp:Button  ID="btnlogin" runat="server" Text="Login" BorderWidth="2px" Width="64px" 
                   CausesValidation="False" OnClientClick="login" CssClass="call-to-action-btn" /> 
                <asp:Label ID="lblError" runat="server" Width="257px" Height="20px" 
                    ForeColor="Red"></asp:Label> 
                </section>
                
            </main>
            
    
        </section>
    

          <asp:AccessDataSource ID="userDb" runat="server" 
                DataFile="~/App_Data/user1.mdb" 
                DeleteCommand="DELETE FROM [User] WHERE [User_ID] = ?" 
                InsertCommand="INSERT INTO [User] ([User_ID], [User_Name], [User_Passwd], [User_Prog]) VALUES (?, ?, ?, ?)" 
                SelectCommand="SELECT * FROM [User]" 
                UpdateCommand="UPDATE [User] SET [User_Name] = ?, [User_Passwd] = ?, [User_Prog] = ? WHERE [User_ID] = ?">
                <DeleteParameters>
                    <asp:Parameter Name="User_ID" Type="Int32" />
                </DeleteParameters>
                <InsertParameters>
                    <asp:Parameter Name="User_ID" Type="Int32" />
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

            <asp:GridView ID="GridView1" runat="server" AutoGenerateColumns="False" 
                DataKeyNames="User_ID" DataSourceID="userDb" CssClass="hidden">
                <Columns>
                    <asp:BoundField DataField="User_ID" HeaderText="User_ID" InsertVisible="False" 
                        ReadOnly="True" SortExpression="User_ID" />
                    <asp:BoundField DataField="User_Name" HeaderText="User_Name" 
                        SortExpression="User_Name" />
                    <asp:BoundField DataField="User_Passwd" HeaderText="User_Passwd" 
                        SortExpression="User_Passwd" />
                    <asp:BoundField DataField="User_Prog" HeaderText="User_Prog" 
                        SortExpression="User_Prog" />
                </Columns>
            </asp:GridView>
      

      <script type="application/javascript">

        document.getElementById("MainContent_btnlogin").addEventListener("click", login)

        function saveCookieLogin(){
            localStorage.setItem('credentials', true)
            document.location.href = "Home.aspx"
        }

        function loginAdmin(){
            localStorage.setItem('admin', true)
        }
        
        function login() {
            let isLoggedIn = false;
            console.log('l')
            let messageLabel = document.getElementById('MainContent_lblError')
            let usernameText = document.getElementById('MainContent_txtUsername').value
            let passwordText = document.getElementById('MainContent_txtPassword').value

            let grid = document.getElementById('MainContent_GridView1')
            let tbody = grid.children[0]
            let trArray = Array.from(tbody.children)
            trArray.shift()

            if(usernameText.length < 1 || passwordText.length < 1){
                messageLabel.textContent = "Please ensure all fields are filled"
                return;
            }

            for(let i = 0; i < trArray.length; i++){
                for(let j = 0; j < trArray[i].cells.length; j++){
                    let username = trArray[i].cells[1].textContent
                    let password = trArray[i].cells[2].textContent

                    if(username === usernameText && password === passwordText){
                        messageLabel.textContent = "Logged in"
                        isLoggedIn = true;
                        if(username === "admin"){
                            loginAdmin()
                        }
                        saveCookieLogin()
                        return;
                    }
                    
                }
            }

            if(!isLoggedIn){
                messageLabel.textContent = "Incorrect credentials"
            }
        }

    </script>
     </form>
</asp:Content>