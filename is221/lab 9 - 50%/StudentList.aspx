<%@ Page Title="" Language="VB" MasterPageFile="~/MasterPages/University.master" AutoEventWireup="false" CodeFile="StudentList.aspx.vb" Inherits="StudentList" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="contentArea" Runat="Server">
    <h2>Student List</h2>
    <asp:GridView ID="GridView1" runat="server" AllowPaging="True" 
    AllowSorting="True" CellPadding="4" DataSourceID="adsStudents" 
    ForeColor="#333333" GridLines="None">
        <AlternatingRowStyle BackColor="White" />
        <Columns>
            <asp:DynamicField DataField="Stu_ID" HeaderText="Stu_ID" />
            <asp:DynamicField DataField="Stu_Name" HeaderText="Stu_Name" />
            <asp:DynamicField DataField="Stu_Prog" HeaderText="Stu_Prog" />
        </Columns>
        <EditRowStyle BackColor="#2461BF" />
        <FooterStyle BackColor="#507CD1" Font-Bold="True" ForeColor="White" />
        <HeaderStyle BackColor="#507CD1" Font-Bold="True" ForeColor="White" />
        <PagerStyle BackColor="#2461BF" ForeColor="White" HorizontalAlign="Center" />
        <RowStyle BackColor="#EFF3FB" />
        <SelectedRowStyle BackColor="#D1DDF1" Font-Bold="True" ForeColor="#333333" />
        <SortedAscendingCellStyle BackColor="#F5F7FB" />
        <SortedAscendingHeaderStyle BackColor="#6D95E1" />
        <SortedDescendingCellStyle BackColor="#E9EBEF" />
        <SortedDescendingHeaderStyle BackColor="#4870BE" />
    </asp:GridView>
    <asp:AccessDataSource ID="adsStudents" runat="server" 
        DataFile="~/App_Data/Students.mdb" SelectCommand="SELECT * FROM [Student]"></asp:AccessDataSource>
</asp:Content>

