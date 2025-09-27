create table template (
  id bigint auto_increment primary key,
  business_id bigint not null,
  name varchar(255) not null,
  template enum ('BUSINESS_INVITE') not null,
  html_content text not null,
  is_super_admin boolean not null default false,
  is_deleted boolean not null default false,
  created_time datetime not null default current_timestamp,
  updated_time datetime null
);
insert into template (
    business_id,
    name,
    template,
    html_content,
    is_super_admin
  )
values (
    0,
    'Business Invite',
    'BUSINESS_INVITE',
    '<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>Welcome to Bill on Trax</title>
    <style>
      body {
        background-color: #f4f7fa;
        margin: 0;
        padding: 20px;
        font-family: ''Segoe UI'', Tahoma, Geneva, Verdana, sans-serif;
        color: #333;
      }

      .container {
        max-width: 600px;
        margin: auto;
        background: #ffffff;
        border-radius: 10px;
        padding: 30px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
        text-align: center;
      }

      .logo {
        margin-bottom: 20px;
      }

      .logo img {
        height: 50px;
      }

      .title {
        font-size: 22px;
        font-weight: bold;
        color: #3b82f6;
        margin-bottom: 10px;
      }

      .message {
        font-size: 16px;
        margin-bottom: 30px;
      }

      .button {
        display: inline-block;
        background-color: #3b82f6;
        color: #ffffff;
        text-decoration: none;
        padding: 12px 24px;
        border-radius: 6px;
        font-weight: bold;
        font-size: 16px;
      }

      .footer {
        margin-top: 40px;
        font-size: 12px;
        color: #888;
      }
    </style>
  </head>
  <body>
    <div class="container">
      <div class="logo">
        <img src="https://yourdomain.com/logo.png" alt="Bill on Trax Logo" />
      </div>

      <div class="title">Welcome to Bill on Trax, {{userName}}!</div>

      <div class="message">
        Thank you for choosing <strong>Bill on Trax</strong> to power your business.<br />
        We’re here to help you manage billing, inventory, services, and more — all in one place.
        <br /><br />
        Click below to get started with setting up your account:
      </div>

      <a target="_blank" href="{{inviteLink}}" class="button">Create Account</a>

      <div class="footer">
        &copy; 2025 Bill on Trax. All rights reserved.<br />
        Need help? Contact us at <a href="mailto:support@billontrax.com">support@billontrax.com</a>
      </div>
    </div>
  </body>
</html>
',
    true
  );