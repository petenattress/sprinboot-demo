<#include "../../page/layout.ftl" >
<@standardPage title="Release type">

  <@errorSummary "form"/>

  <@form formAction>
    <@input.radios "form.releaseType" "Are you reporting discharge in excess of an oil discharge permit?" radioOptions />

    <@button.submit "Next"/>
  </@form>
</@>