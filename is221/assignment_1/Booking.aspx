<%@ Page Title="" Language="VB" MasterPageFile="~/Site.master" AutoEventWireup="false" CodeFile="Booking.aspx.vb" Inherits="Booking" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">

    <script>

        function checkLoginCookies(){
            let creds = localStorage.getItem('credentials')
            if(creds == true || creds == 'true'){
                return;
            }
            else if(creds !== true || creds !== 'true'){
                 document.location.href = "unauthorised.htm"
            }
         }

         checkLoginCookies()
    </script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    
    <section class="booking-main">
      
        <div class="booking-cont">
            <h1>Book an Appointment</h1>
            <form>
                <div class="login-form-inputfield">
                    <div class="flex">
                        <asp:Label ID="lbFname" runat="server" Text="First Name: " CssClass="form-label"></asp:Label>
                        <asp:TextBox ID="txtFname" runat="server"></asp:TextBox>

                        <asp:Label ID="lbSname" runat="server" Text="Surname: " CssClass="form-label"></asp:Label>
                        <asp:TextBox ID="txtSname" runat="server" ></asp:TextBox>

                         <asp:Label ID="Label1" runat="server" Text="email: " CssClass="form-label"></asp:Label>
                        <asp:TextBox ID="txtEmail" runat="server" ></asp:TextBox>
                    </div>

                    <asp:Label ID="Label5" runat="server" Text="Select date: " CssClass="form-label"></asp:Label>
                    <asp:Calendar ID="AppCalendar" runat="server"></asp:Calendar>
               
                    <asp:Label ID="Label2" runat="server" Text="Select time: " CssClass="form-label"></asp:Label>
                    <asp:DropDownList ID="dropDownTime" runat="server">
                        <asp:ListItem>9am - 10am</asp:ListItem>
                        <asp:ListItem>11am - 12pm</asp:ListItem>
                        <asp:ListItem>3pm - 4pm</asp:ListItem>
                    </asp:DropDownList>

                     <div class="symptoms-input">
                        <asp:Label ID="lbSymp" runat="server" Text="Describe your symptoms: " CssClass="form-label"></asp:Label>
                        <asp:TextBox ID="txtSymp" runat="server" TextMode="MultiLine" Rows="8"></asp:TextBox>

                    </div>

                    <asp:Label ID="Label3" runat="server" Text="Chose appointment type: " CssClass="form-label"></asp:Label>
                    <asp:CheckBoxList ID="checkBoxType" runat="server">
                        <asp:ListItem>general</asp:ListItem>
                        <asp:ListItem>cardiovascular</asp:ListItem>
                        <asp:ListItem>eye clinic</asp:ListItem>
                    </asp:CheckBoxList>

                
                </div>

                <asp:Button ID="btnAddUser" runat="server" Text="Book" CssClass="call-to-action-btn" />
                <br />
                <asp:Label ID="formOutputLabel" runat="server" Text=""></asp:Label>
            <asp:AccessDataSource ID="dbAppointment" runat="server" 
                    DataFile="~/App_Data/user1.mdb" 
                    DeleteCommand="DELETE FROM [Appointment] WHERE [appt_id] = ?" 
                    InsertCommand="INSERT INTO [Appointment] ([patient_fname], [patient_sname], [patient_email], [appt_date], [appt_time], [appt_symptoms], [appt_type]) VALUES (?, ?, ?, ?, ?, ?, ?)" 
                    SelectCommand="SELECT * FROM [Appointment]" 
                    UpdateCommand="UPDATE [Appointment] SET [patient_fname] = ?, [patient_sname] = ?, [patient_email] = ?, [appt_date] = ?, [appt_time] = ?, [appt_symptoms] = ?, [appt_type] = ? WHERE [appt_id] = ?">
                <DeleteParameters>
                    <asp:Parameter Name="appt_id" Type="Int32" />
                </DeleteParameters>
                <InsertParameters>
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

            </form>
        </div
     <section>
             <table class="appointment-table">
        <caption>
          Appointment Booking Times
        </caption>
        <tr class="table-title-row">
          <th>Day</th>
          <th>Time</th>
          <th>Doctor</th>
        </tr>

        <tr>
          <td>Monday</td>
          <td>9am - 10am</td>
          <td>Dr Malfoy</td>
         
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>11am - 12pm</td>
          <td>Dr Snape</td>
           
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>3pm - 4pm</td>
          <td>Dr Weasley</td>

        </tr>

        <tr>
          <td>Wednesday</td>
          <td>9am - 10am</td>
          <td>Dr Potter</td>
      
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>11am - 12pm</td>
          <td>Dr Granger</td>

        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>3pm - 4pm</td>
          <td>Dr Dumbeldore</td>
        </tr>

        <tr>
          <td>Friday</td>
          <td>9am - 10am</td>
          <td>Dr Weasley</td
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>11am - 12pm</td>
          <td>Dr Malfoy</td>
           
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>3pm - 4pm</td>
          <td>Dr Potter</td>
      
        </tr>
      </table>
        </section>
    </section>
    

</asp:Content>



