# Member 1 -> Member 2 Handoff Checklist

Use this before asking Member 2 to start.

- [ ] `SmartDevice` is stable and signatures are frozen:
  - `executeCommand(Object command)`
  - `getStatusSummary()`
- [ ] `DeviceStatus` enum added (`ONLINE`, `OFFLINE`, `ERROR`)
- [ ] `DeviceEvent` is immutable (`final` fields, no setters)
- [ ] `DeviceRegistry` singleton works via `getInstance()`
- [ ] `EventBus` singleton works with `subscribe()` and `publish()`
- [ ] Event type strings use `AppConstants` (no raw strings)
- [ ] Observer contracts are ready (`DeviceObserver`, `DeviceEventListener`)
- [ ] Branch pushed and pull message sent to team

Suggested message:

`M1 base is ready: SmartDevice + DeviceRegistry + DeviceEvent + EventBus + observer interfaces are stable. Pull latest and start SmartAC/Command layer.`
