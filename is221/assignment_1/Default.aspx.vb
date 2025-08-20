Imports System.Data
Imports System.Data.OleDb

Partial Class _Default
    Inherits System.Web.UI.Page

    Protected Sub btnlogin_Click(ByVal sender As Object, ByVal e As System.EventArgs) Handles btnlogin.Click
        If txtUsername.Text = "" Or txtPassword.Text = "" Then
            lblError.Text = "Invalid Username or Password"
        Else
            Dim connect As String = "Provider=Microsoft.Jet.OleDb.4.0;" & _
            "Data Source=|DataDirectory|user1.mdb"

            Dim query As String
            query = "Select Count(*) From [User] Where User_Name = '" + txtUsername.Text + "' And User_Passwd = '" + txtPassword.Text + "'"
            Dim result As Integer = 0
            Using conn As New OleDbConnection(connect)
                Using cmd As New OleDbCommand(query, conn)
                    cmd.Parameters.AddWithValue("@User_Name", txtUsername.Text)
                    cmd.Parameters.AddWithValue("@User_Passwd", txtPassword.Text)
                    conn.Open()
                    result = cmd.ExecuteScalar()
                End Using
            End Using
            If result = 1 Then
                Session.Timeout = 60
                Session("username") = txtUsername.Text
                Response.Redirect("About.aspx")
            Else
                lblError.Text = "Invalid Password"
                txtPassword.Focus()
            End If
        End If
    End Sub
End Class
