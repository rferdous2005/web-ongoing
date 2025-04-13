const sliderData = [
    {
      title: "Reinforce the security of SME homepages",
      text: `Provide web vulnerability inspection and web security enhancement tools (Whistle Castle, Cyber Shelter) service for SMEs.
            Constantly monitor for threats and secure your system proactively.`
    },
    {
      title: "Real-time Cyber Monitoring",
      text: "Constantly monitor for threats and secure your system proactively. Provide web vulnerability inspection and web security enhancement tools (Whistle Castle, Cyber Shelter) service for SMEs."
    },
    {
      title: "Nationwide Cyber Defense",
      text: "Defending infrastructure and citizens from malicious cyber attacks. Provide web vulnerability inspection and web security enhancement tools (Whistle Castle, Cyber Shelter) service for SMEs"
    }
  ];

function formatAMPM(date) {
    let hours = date.getHours();
    let minutes = date.getMinutes();
    const ampm = hours >= 12 ? 'PM' : 'AM';

    hours = hours % 12;
    hours = hours ? hours : 12; // the hour '0' should be '12'
    minutes = minutes < 10 ? '0' + minutes : minutes;

    const strTime = `${hours}:${minutes} ${ampm}`;
    return strTime;
  }

  function getFormattedDateTime() {
    const date = new Date();
    const monthNames = [
      "January", "February", "March", "April", "May", "June",
      "July", "August", "September", "October", "November", "December"
    ];

    const day = String(date.getDate()).padStart(2, '0');
    const month = monthNames[date.getMonth()];
    const year = date.getFullYear();
    const time = formatAMPM(date);

    return `${month} ${day}, ${year} ${time}`;
  }

  function updateClock() {
    $("#currentDateTime").text(getFormattedDateTime());
  }
