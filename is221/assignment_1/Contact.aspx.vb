
Partial Class Contact
    Inherits System.Web.UI.Page

    Protected Sub sendEmail_Click(ByVal sender As Object, ByVal e As System.EventArgs) Handles sendEmail.Click
        emailDb.InsertParameters("email").DefaultValue = txtName.Text
        emailDb.InsertParameters("full_name").DefaultValue = txtEmail.Text
        emailDb.InsertParameters("message").DefaultValue = txtEmailBody.Text
        emailDb.Insert()
        Response.Redirect("~/Success.aspx")
        emailSuccess.Text = "Email Sent Successfully"
    End Sub
End Class
