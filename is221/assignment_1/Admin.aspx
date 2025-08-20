<%@ Page Title="" Language="VB" MasterPageFile="~/Site.master" %>

<script runat="server">

</script>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
<script>

        function checkLoginCookies(){
            let creds = localStorage.getItem('credentials')
            let admin = localStorage.getItem('admin')
            if(creds !== true && creds !== 'true'){
                 document.location.href = "http://localhost:52280/ass%20-%205_14/unauthorised.htm"
            }

            if(admin != true && admin != 'true'){
                 document.location.href = "http://localhost:52280/ass%20-%205_14/unauthorised.htm"
            }
         }

         checkLoginCookies()
    </script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">

    <section class="admin-users">
        <h1>List of Users</h1>
            <asp:GridView ID="User" runat="server" AllowPaging="True" 
            AllowSorting="True" AutoGenerateColumns="False" DataKeyNames="User_ID" 
            DataSourceID="userDB">
                <Columns>
                    <asp:BoundField DataField="User_ID" HeaderText="ID" InsertVisible="False" 
                        ReadOnly="True" SortExpression="User_ID" />
                    <asp:BoundField DataField="User_Name" HeaderText="Username" 
                        SortExpression="User_Name" />
                    <asp:BoundField DataField="User_Passwd" HeaderText="Password" 
                        SortExpression="User_Passwd" />
                    <asp:CommandField ShowDeleteButton="True" ShowEditButton="True" 
                        HeaderText="Edit" />
                </Columns>
        </asp:GridView>
    </section>

    <section class="admin-users">
        <h1>List of Appointments</h1>
            <asp:GridView ID="GridView1" runat="server" AllowPaging="True" 
            AllowSorting="True" AutoGenerateColumns="False" DataKeyNames="appt_id" 
            DataSourceID="appointmentDB">
                <Columns>
                    <asp:BoundField DataField="appt_id" HeaderText="ID" InsertVisible="False" 
                        ReadOnly="True" SortExpression="appt_id" />
                    <asp:BoundField DataField="patient_fname" HeaderText="Name" 
                        SortExpression="patient_fname" />
                    <asp:BoundField DataField="patient_sname" HeaderText="Surname" 
                        SortExpression="patient_sname" />
                    <asp:BoundField DataField="patient_email" HeaderText="Email" 
                        SortExpression="patient_email" />
                    <asp:BoundField DataField="appt_date" HeaderText="Date" 
                        SortExpression="appt_date" />
                    <asp:BoundField DataField="appt_time" HeaderText="Time Slot" 
                        SortExpression="appt_time" />
                    <asp:BoundField DataField="appt_symptoms" HeaderText="Symptoms" 
                        SortExpression="appt_symptoms" />
                    <asp:BoundField DataField="appt_type" HeaderText="Type" 
                        SortExpression="appt_type" />
                    <asp:CommandField HeaderText="Edit" ShowDeleteButton="True" 
                        ShowEditButton="True" />
                </Columns>
        </asp:GridView>
    </section>
   
    <section class="admin-users">
        <h1>List of Emails </h1>
            <asp:GridView ID="GridView2" runat="server" AllowPaging="True" 
            AllowSorting="True" AutoGenerateColumns="False" DataKeyNames="ID" 
            DataSourceID="emailDB">
                <Columns>
                    <asp:BoundField DataField="email" HeaderText="Email" SortExpression="email" />
                    <asp:BoundField DataField="full_name" HeaderText="Name" 
                        SortExpression="full_name" />
                    <asp:BoundField DataField="message" HeaderText="Message" 
                        SortExpression="message">
                    <ControlStyle Width="500px" />
                    </asp:BoundField>
                    <asp:CommandField ShowDeleteButton="True" ShowEditButton="True" />
                </Columns>
        </asp:GridView>
    </section>
    <asp:AccessDataSource ID="emailDB" runat="server" 
        DataFile="~/App_Data/user1.mdb" 
        DeleteCommand="DELETE FROM [Email] WHERE [ID] = ?" 
        InsertCommand="INSERT INTO [Email] ([ID], [email], [full_name], [message]) VALUES (?, ?, ?, ?)" 
        SelectCommand="SELECT * FROM [Email]" 
        
        UpdateCommand="UPDATE [Email] SET [email] = ?, [full_name] = ?, [message] = ? WHERE [ID] = ?">
        <DeleteParameters>
            <asp:Parameter Name="ID" Type="Int32" />
        </DeleteParameters>
        <InsertParameters>
            <asp:Parameter Name="ID" Type="Int32" />
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


    <asp:AccessDataSource ID="userDB" runat="server" 
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

     <asp:AccessDataSource ID="appointmentDB" runat="server" 
        DataFile="~/App_Data/user1.mdb" 
        DeleteCommand="DELETE FROM [Appointment] WHERE [appt_id] = ?" 
        InsertCommand="INSERT INTO [Appointment] ([appt_id], [patient_fname], [patient_sname], [patient_email], [appt_date], [appt_time], [appt_symptoms], [appt_type]) VALUES (?, ?, ?, ?, ?, ?, ?, ?)" 
        SelectCommand="SELECT * FROM [Appointment]" 
        UpdateCommand="UPDATE [Appointment] SET [patient_fname] = ?, [patient_sname] = ?, [patient_email] = ?, [appt_date] = ?, [appt_time] = ?, [appt_symptoms] = ?, [appt_type] = ? WHERE [appt_id] = ?">
        <DeleteParameters>
            <asp:Parameter Name="appt_id" Type="Int32" />
        </DeleteParameters>
        <InsertParameters>
            <asp:Parameter Name="appt_id" Type="Int32" />
            <asp:Parameter Name="patient_fname" Type="String" />
            <asp:Parameter Name="patient_sname" Type="String" />
            <asp:Parameter Name="patient_email" Type="String" />
            <asp:Parameter Name="appt_date" Type="DateTime" />
            <asp:Parameter Name="appt_time" Type="String" />
            <asp:Parameter Name="appt_symptoms" Type="String" />
            <asp:Parameter Name="appt_type" Type="String" />
        </InsertParameters>
        <UpdateParameters>
            <asp:Parameter Name="patient_fname" Type="String" />
            <asp:Parameter Name="patient_sname" Type="String" />
            <asp:Parameter Name="patient_email" Type="String" />
            <asp:Parameter Name="appt_date" Type="DateTime" />
            <asp:Parameter Name="appt_time" Type="String" />
            <asp:Parameter Name="appt_symptoms" Type="String" />
            <asp:Parameter Name="appt_type" Type="String" />
            <asp:Parameter Name="appt_id" Type="Int32" />
        </UpdateParameters>
    </asp:AccessDataSource>


</asp:Content>

