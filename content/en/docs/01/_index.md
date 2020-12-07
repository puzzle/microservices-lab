---
title: "1. CHANGEME Sample Chapter"
weight: 1
sectionnumber: 1
---

## Title 1

{{% alert title="Note" color="primary" %}}
Sample Note
{{% /alert %}}

Sample code block:
```bash
echo "Hello World!"
```

{{< onlyWhen variant1 >}}
This is only rendered when `enabledModule` in `config.toml` contains `variant1`
{{< /onlyWhen >}}
