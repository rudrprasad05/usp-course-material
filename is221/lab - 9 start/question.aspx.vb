
Partial Class question
    Inherits System.Web.UI.Page

    Protected Sub btnAdd_Click(ByVal sender As Object, ByVal e As System.EventArgs) Handles btnAdd.Click

        lbQuestionText.Text = "The following details have been saved to our database"
        lbQuestionText.Text = "The question is: " & txtQestion.Text
        lbQuestionText.Text &= "The answer is: " & txtAnswer.Text & "<br />"
        lbQuestionText.Text &= "<ul>"
        lbQuestionText.Text &= "</ul>"

        addQuestion.InsertParameters("Question").DefaultValue = txtQestion.Text
        addQuestion.InsertParameters("Answer").DefaultValue = txtAnswer.Text
        addQuestion.Insert()
        

    End Sub
End Class
