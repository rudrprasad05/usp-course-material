<%@ Page Title="" Language="VB" MasterPageFile="~/Site.master" AutoEventWireup="false" CodeFile="Contact.aspx.vb" Inherits="Contact" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">

      <section class="our-contacts-page">
          <h2 class="our-contacts-text col-span-1">Got Questions? Contact Us</h2>
          <article class="our-contacts-cards">
            <h3>Mobile</h3>
            <p>679 9234 456</p>
          </article>

          <article class="our-contacts-cards">
            <h3>Email</h3>
            <p>info@nomorefever.com</p>
          </article>

          <article class="our-contacts-cards">
            <h3>Address</h3>
            <address>8 Batman Road, Toorak, Suva</address>
          </article>
        </section>
    
        <h2 class="our-contacts-text">Drop an Email</h2>

        <form action="Contact.aspx" id="email-form">
            <div class="email-form-inputfield">
                <div class="flex">
                    <asp:Label ID="lbName" runat="server" Text="Name: "></asp:Label>
                    <asp:TextBox ID="txtName" Placeholder="enter you full name" runat="server"></asp:TextBox>
                    <asp:Label ID="lbEMail" runat="server" Text="Email: "></asp:Label>
                    <asp:TextBox ID="txtEmail" Placeholder="enter your email" runat="server"></asp:TextBox>
                </div>
                <div class="flex">
                    <asp:Label ID="lbEmailBody" runat="server" Text="Message: "></asp:Label>
                    <asp:TextBox ID="txtEmailBody" Placeholder="enter the body of the email" runat="server" TextMode="MultiLine" Rows="8"></asp:TextBox>
            
                </div>

                <asp:Button ID="sendEmail" runat="server" Text="Send Email" CssClass="call-to-action-btn"/>
                 <asp:Label ID="emailSuccess" runat="server" Text=""></asp:Label>
            </div>



      
        </form>
          <asp:AccessDataSource ID="emailDb" runat="server" DataFile="~/App_Data/user1.mdb" 
                DeleteCommand="DELETE FROM [Email] WHERE [ID] = ?" 
                InsertCommand="INSERT INTO [Email] ([email], [full_name], [message]) VALUES (?, ?, ?)" 
                SelectCommand="SELECT * FROM [Email]" 
                UpdateCommand="UPDATE [Email] SET [email] = ?, [full_name] = ?, [message] = ? WHERE [ID] = ?">
            <DeleteParameters>
                <asp:Parameter Name="ID" Type="Int32" />
            </DeleteParameters>
            <InsertParameters>
                <asp:Parameter Name="email" Type="String" />
                <asp:Parameter Name="full_name" Type="String" />
                <asp:Parameter Name="message" Type="String" />
            </InsertParameters>
            <UpdateParameters>
                <asp:Parameter Name="email" Type="String" />
                <asp:Parameter Name="full_name" Type="String" />
                <asp:Parameter Name="message" Type="String" />
                <asp:Parameter Name="ID" Type="Int32" />
            </UpdateParameters>
            </asp:AccessDataSource>
</asp:Content>

