<%@ Page Title="About Me" Language="VB" MasterPageFile="~/MasterPages/University.master" AutoEventWireup="false" CodeFile="aboutme.aspx.vb" Inherits="aboutme" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="contentArea" Runat="Server">
<h1>About Rudr Prasad</h1>
    <p>
      Hi everyone. I love code<br />
      I'm from <strong>Suva, Fiji</strong>, and I am a second year Bachelor of
      Software Engineering student at University of the South Pacific.
    </p>

    <h2>My Courses This Semester</h2>
    <ol>
      <li>CS 211</li>
      <li>CS 230</li>
      <li>IS 221</li>
      <li>IS 222</li>
    </ol>
    <h2>My Favourite Websites</h2>
    <ul>
      <li>
        <a href="http://www.usp.ac.fj" target="blank"> USP Home</a>
      </li>
      <li>
        <a href="http://www.elearn.usp.ac.fj" target="blank"> Moodle </a>
      </li>
      <li>
        <a href="https://www.google.com/" target="blank"> Google </a>
      </li>
    </ul>
    <h2>Pictures of Me</h2>
    <img
      src="images/img1.jpeg"
      alt="me coding"
      title=" just too good"
      width="400px"
      height="400px"
    />
    <img
      src="images/img2.jpeg"
      alt="solo shot"
      title="me coding"
      width="400px"
      height="400px"
    />
</asp:Content>

