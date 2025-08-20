
Partial Class Booking
    Inherits System.Web.UI.Page

    Protected Sub btnAddUser_Click(ByVal sender As Object, ByVal e As System.EventArgs) Handles btnAddUser.Click
        formOutputLabel.Text = "The following details have been saved to our database" & "<br />"
        formOutputLabel.Text = "Your name is: " & txtFname.Text() & " " & txtSname.Text & "<br />"
        formOutputLabel.Text &= "Your email is: " & txtEmail.Text & "<br />"
        formOutputLabel.Text &= "The sympotms are: " & txtSymp.Text & "<br />"
        formOutputLabel.Text &= "The selected time period is: " & dropDownTime.SelectedValue & "<br />"
        formOutputLabel.Text &= "The selected day is: " & AppCalendar.SelectedDate & "<br />"

        dbAppointment.InsertParameters("patient_fname").DefaultValue = txtFname.Text
        dbAppointment.InsertParameters("patient_sname").DefaultValue = txtSname.Text
        dbAppointment.InsertParameters("patient_email").DefaultValue = txtEmail.Text
        dbAppointment.InsertParameters("appt_date").DefaultValue = AppCalendar.SelectedDate
        dbAppointment.InsertParameters("appt_time").DefaultValue = dropDownTime.SelectedValue
        dbAppointment.InsertParameters("appt_symptoms").DefaultValue = txtSymp.Text

        For Each item As ListItem In checkBoxType.Items
            If item.Selected Then
                formOutputLabel.Text &= "The answer is: " & item.Value & "<br />"
                dbAppointment.InsertParameters("appt_type").DefaultValue = item.Value
            End If
        Next

        dbAppointment.Insert()
        Response.Redirect("~/Success.aspx")

    End Sub
End Class
