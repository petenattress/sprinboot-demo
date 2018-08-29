<#include "page/layout.ftl" >
<@standardPage title="Enter person details">

  <@errorSummary "personForm"/>

  <form action="${formAction}" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <@input.text "personForm.name" "Your name"/>
    <@input.text "personForm.age" "Your age"/>

    <@input.radioBoolean path="personForm.drivingLicence" questionPrompt="Do you have a driving licence?" yesPrompt="Yep"/>

    <@input.checkboxes "personForm.interests" "Select your interests" interestOptions/>

    <@button.submit "Next"/>
  </form>
</@>