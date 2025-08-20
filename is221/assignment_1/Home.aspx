<%@ Page Title="Home Page" Language="VB" MasterPageFile="~/Site.Master" AutoEventWireup="false"
    CodeFile="Home.aspx.vb" Inherits="_Default" %>

<asp:Content ID="HeaderContent" runat="server" ContentPlaceHolderID="HeadContent">
</asp:Content>
<asp:Content ID="BodyContent" runat="server" ContentPlaceHolderID="MainContent">
     <div class="hero">
      <h1>Book an Appointment Now</h1>
      <h3>
        Get the <strong>best Doctors</strong> and state of the art medical
        facilities. A true promise of quality
      </h3>
      <div class="call-to-action-cont">
          <asp:HyperLink ID="HyperLink1" runat="server" NavigateUrl="~/Booking.aspx" CssClass="call-to-action-btn">
              Booknow
         </asp:HyperLink>
        <asp:HyperLink ID="HyperLink2" runat="server" NavigateUrl="~/Contact.aspx" CssClass="call-to-action-btn">
            Contact
         </asp:HyperLink>
      </div>
    </div>

    <h2 class="our-services-text col-span-1">Our Services</h2>
      <p class="our-services-paragraph col-span-1">
        Our clinic provides the <em>best</em> medical facilities in Suva. With
        state of the art medical imaging technology and the best doctors in the
        Ocenia-Pacific region, get the treatment your body deserves
      </p>
     <section class="our-services">
      
      <article class="our-services-cards">
        <img
          title="stethescope"
          width="400"
          height="280"
          src="images/stetescope_image.jpg"
          alt="image of stethescope"
        />
        <h3>Appointment Booking</h3>
        <p>
          Tired of waiting lines? <strong>Book an appointment</strong> and skip
          the wait.
        </p>
      </article>

      <article class="our-services-cards">
        <img
          title="checkup"
          width="400"
          height="280"
          src="images/doctor_image.jpg"
          alt="general checkup image"
        />
        <h3>General Checkup</h3>
        <p>A state-of-the-art full body checkup</p>
      </article>

      <article class="our-services-cards">
        <img
          title="heart monitor"
          width="400"
          height="280"
          src="images/ecg_image.jpg"
          alt="heart monitor"
        />
        <h3>Surgery</h3>
        <p>Sticks and stones may break your bones, but we'll help fix them</p>
      </article>
    </section>

     <section class="appointment-times">
      <h2>Chose an Appointment Slot</h2>
      <table class="appointment-table">
        <caption>
          Appointment Booking Times
        </caption>
        <tr class="table-title-row">
          <th>Day</th>
          <th>Time</th>
          <th>Doctor</th>
          <th>&nbsp;</th>
        </tr>

        <tr>
          <td>Monday</td>
          <td>9am - 10am</td>
          <td>Dr Malfoy</td>
          <td><a href="Booking.aspx?day=monday&time=9">Book now</a></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>11am - 12pm</td>
          <td>Dr Snape</td>
           <td><a href="Booking.aspx?day=monday&time=11">Book now</a></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>3pm - 4pm</td>
          <td>Dr Weasley</td>
           <td><a href="Booking.aspx?day=monday&time=3">Book now</a></td>
        </tr>

        <tr>
          <td>Wednesday</td>
          <td>9am - 10am</td>
          <td>Dr Potter</td>
           <td><a href="Booking.aspx?day=wednesday&time=9">Book now</a></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>11am - 12pm</td>
          <td>Dr Granger</td>
           <td><a href="Booking.aspx?day=wednesday&time=11">Book now</a></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>3pm - 4pm</td>
          <td>Dr Dumbeldore</td>
           <td><a href="Booking.aspx?day=wednesday&time=3">Book now</a></td>
        </tr>

        <tr>
          <td>Friday</td>
          <td>9am - 10am</td>
          <td>Dr Weasley</td>
           <td><a href="Booking.aspx?day=friday&time=9">Book now</a></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>11am - 12pm</td>
          <td>Dr Malfoy</td>
           <td><a href="Booking.aspx?day=friday&time=11">Book now</a></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>3pm - 4pm</td>
          <td>Dr Potter</td>
           <td><a href="Booking.aspx?day=friday&time=3">Book now</a></td>
        </tr>
      </table>
    </section>

    <section class="our-contacts">
      <h2 class="our-contacts-text col-span-1">Got Questions? Contact Us</h2>
      <article class="our-contacts-cards">
        <h3>Mobile</h3>
        <p>679 9234 456</p>
      </article>

      <article class="our-contacts-cards">
        <h3>Email</h3>
        <p>info@nomorefever.com</p>
      </article>

      <article class="our-contacts-cards">
        <h3>Address</h3>
        <address>8 Batman Road, Toorak, Suva</address>
      </article>
    </section>


</asp:Content>