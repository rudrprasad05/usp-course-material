
Partial Class Register
    Inherits System.Web.UI.Page

    Protected Sub btnRegister_Click(ByVal sender As Object, ByVal e As System.EventArgs) Handles btnRegister.Click
        lblVerify.Text = "The following details have been saved to our database"
        lblVerify.Text = lblVerify.Text & "Your name: " & txtName.Text & "<br />"
        lblVerify.Text &= "Your Student ID: " & txtStudentID.Text & "<br />"
        lblVerify.Text &= "Your Password: " & txtPassword.Text & "<br />"
        lblVerify.Text &= "You are registered in the " & ddlProgram.SelectedValue & " Program " & "<br />"
        lblVerify.Text &= "and your major areas of study are:" & "<br />"
        lblVerify.Text &= "<ul>"

        For Each item As ListItem In cblMajors.Items
            If item.Selected Then
                lblVerify.Text &= "<li>" & item.Value & "</li>"
            End If
        Next
        lblVerify.Text &= "</ul>"


        adsStudents.InsertParameters("Stu_ID").DefaultValue = txtStudentID.Text
        adsStudents.InsertParameters("Stu_Name").DefaultValue = txtName.Text
        adsStudents.InsertParameters("Stu_Passwd").DefaultValue = txtPassword.Text
        adsStudents.InsertParameters("Stu_Prog").DefaultValue = ddlProgram.SelectedValue
        adsStudents.Insert()
        'add a record in the majors table for each major per student 
        adsMajors.InsertParameters("Stu_ID").DefaultValue = txtStudentID.Text
        For Each item As ListItem In cblMajors.Items
            If item.Selected Then
                adsMajors.InsertParameters("Stu_Majors").DefaultValue = item.Value
                adsMajors.Insert()
            End If
        Next

    End Sub
End Class
