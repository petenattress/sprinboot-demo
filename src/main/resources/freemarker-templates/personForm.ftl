<#include "page/standardPage.ftl">
<#include "widgets/input.ftl">
<@standardPage title="Enter person details">
  <form action="${formAction}" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <@input "personForm.name" "Your name"/>
    <@input "personForm.age" "Your age"/>

    <@radioBoolean path="personForm.drivingLicence" questionPrompt="Do you have a driving licence?" yesPrompt="Yep"/>

    <@checkboxes "personForm.interests" "Select your interests" interestOptions/>

    <button type="submit" class="govuk-button">Submit</button>
  </form>
</@>