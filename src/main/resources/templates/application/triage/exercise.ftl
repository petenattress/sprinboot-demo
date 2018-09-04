<#include "../../page/layout.ftl" >
<@standardPage title="Exercise">

  <@errorSummary "form"/>

  <@form formAction>
    <@input.radioBoolean path="form.exercise"
      questionPrompt="Are you reporting discharge in excess of an oil discharge permit?"
      yesPrompt="Exercise submission" noPrompt="Actual release"/>

    <@button.submit "Next"/>
  </@form>
</@>