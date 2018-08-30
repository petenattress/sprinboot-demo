<#include "page/layout.ftl">
<@standardPage "Operator Dashboard">
  <#list dashboardEntries>
    <ul class="govuk-list--bullet">
      <#items as dashboardEntry>
        <li><a href="/application/${dashboardEntry.applicationId}/triage/release-type">Application ID: ${dashboardEntry.applicationId}</a></li>
      </#items>
    </ul>
  <#else>
  <p>No dashboard entries</p>
  </#list>
  <@form formAction>
    <@button.submit "Create new application"/>
  </@form>
</@standardPage>