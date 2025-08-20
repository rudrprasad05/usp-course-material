
Partial Class Register
    Inherits System.Web.UI.Page

    Protected Sub btnAddUser_Click(ByVal sender As Object, ByVal e As System.EventArgs) Handles btnAddUser.Click
        addUser.InsertParameters("User_Name").DefaultValue = txtUsername.Text
        addUser.InsertParameters("User_Passwd").DefaultValue = txtPassword.Text()
        addUser.InsertParameters("User_Prog").DefaultValue = txtPassword.Text()
        addUser.Insert()

        Response.Redirect("~/Home.aspx")

    End Sub
End Class
