
Partial Class question
    Inherits System.Web.UI.Page

    Protected Sub btnAdd_Click(ByVal sender As Object, ByVal e As System.EventArgs) Handles btnAdd.Click

        lbQuestionText.Text = "The following details have been saved to our database"
        lbQuestionText.Text = "The question is: " & txtQestion.Text
        lbQuestionText.Text &= "The answer is: " & txtAnswer.Text & "<br />"

        addQuestion.InsertParameters("Question").DefaultValue = txtQestion.Text
        addQuestion.InsertParameters("Answer").DefaultValue = txtAnswer.Text
        addQuestion.Insert()

    End Sub

    Protected Sub btnClear_Click(ByVal sender As Object, ByVal e As System.EventArgs) Handles btnClear.Click
        txtQestion.Text = ""
        txtAnswer.Text = ""
    End Sub
End Class
